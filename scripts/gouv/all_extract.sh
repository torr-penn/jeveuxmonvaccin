#!/bin/bash
cd /work/jeveuxmonvaccin/scripts/gouv/
./getVacci.sh
if [ "$1" != "" ]; then
  FILE1="$1"
else
  FILE1=vacci-tot.csv
fi

function fillhabitants() {

  habitanttext=""
  deptid=$1
  habitanttext=$(cat habitants.txt | grep -o "^$deptid;.*" | awk -F\; '{print " "$3" ";}')
  echo "$deptid $habitanttext "
}
function fillage() {

  classeagetext=""
  clageid=$1
  if [ "$clageid" == 80 ]; then
    classeagetext=" de 80 ans et plus "
  fi
  if [ "$clageid" == 79 ]; then
    classeagetext=" de 75 à 79 ans "
  fi
  if [ "$clageid" == 74 ]; then
    classeagetext=" de 70 à 74 ans "
  fi
  if [ "$clageid" == 69 ]; then
    classeagetext=" de 60 à 69 ans "
  fi
  if [ "$clageid" == 59 ]; then
    classeagetext=" de 50 à 59 ans "
  fi
  if [ "$clageid" == 49 ]; then
    classeagetext=" de 40 à 49 ans "
  fi
  if [ "$clageid" == 39 ]; then
    classeagetext=" de 30 à 39 ans "
  fi
  if [ "$clageid" == 29 ]; then
    classeagetext=" de 25 à 29 ans "
  fi
  if [ "$clageid" == 24 ]; then
    classeagetext=" de 18 à 24 ans "
  fi
  if [ "$clageid" == 17 ]; then
    classeagetext=" de 10 à 17 ans "
  fi
  if [ "$clageid" == 9 ]; then
    classeagetext=" de moins de 10 ans "
  fi
  if [ "$clageid" == 0 ]; then
    classeagetext=" "
  fi

}

function close_message() {
  for ca in 0 9 17 24 29 39 49 59 69 74 79 80; do
    if test -f "message/data_${ca}_1.js"; then
      echo "}" >>message/data_${ca}_1.js

      mymax=$(cat message/data_${ca}_1.js | awk -F: '{print $2;}' | grep "." | sort -n | tail -1)
      mymin=$(cat message/data_${ca}_1.js | awk -F: '{print $2;}' | grep "." | sort -rn | tail -1)
      echo "var vacc1_${ca}Min =${mymin};" >>message/data_${ca}_1.js
      echo "var vacc1_${ca}Max =${mymax};" >>message/data_${ca}_1.js
    fi

    if test -f "message/data_${ca}_2.js"; then
      echo "}" >>message/data_${ca}_2.js
      mymax=$(cat message/data_${ca}_2.js | awk -F: '{print $2;}' | grep "." | sort -n | tail -1)
      mymin=$(cat message/data_${ca}_2.js | awk -F: '{print $2;}' | grep "." | sort -rn | tail -1)
      echo "var vacc2_${ca}Min =${mymin};" >>message/data_${ca}_2.js
      echo "var vacc2_${ca}Max =${mymax};" >>message/data_${ca}_2.js

    fi

  done
}

function process_all() {
  while IFS= read -r line; do
    dept=$(echo $line | awk -F\; ' {print $1;}')
    clage=$(echo $line | awk -F\; ' {print $2;}')
    fillage $clage
    fillhabitants $dept
    if [ "$classeagetext" != "" ] && [ "$habitanttext" != "" ]; then
      #echo " bon dep : $dept et ok $classeagetext "
      jour=$(echo $line | awk -F\; ' {print $3;}')
      jjour=$(echo $jour | awk -F- ' {print $3"/"$2;}')
      nbpeople=$(echo $line | awk -F\; ' {print $6;}')
      premdose=$(echo $line | awk -F\; ' {print $7;}')
      deuxdose=$(echo $line | awk -F\; ' {print $8;}')
      nbsanstot=$(echo "((100-$deuxdose)*$nbpeople/100)" | bc | numfmt --grouping)
      pctlefttot=$(echo "((100-$deuxdose))" | bc)

      nbsansprem=$(echo "((100-$premdose)*$nbpeople/100)" | bc | numfmt --grouping)
      pctleftprem=$(echo "((100-$premdose))" | bc)

      message="au $jjour, $nbsansprem $habitanttext $classeagetext ($pctleftprem%)  n'ont reçu aucune dose;$jjour;$nbsansprem;$clage;$dept;$pctleftprem;1;"
      echo $message >>message/information_$dept.txt
      message="au $jjour, $nbsanstot $habitanttext $classeagetext ($pctlefttot%) ne sont pas totalement vaccinés;$jjour;$nbsanstot;$clage;$dept;$pctlefttot;2;"
      echo $message >>message/information_$dept.txt
      special_dep $dept
      if [ "$mapDep" != "977" ] && [ "$mapDep" != "978" ]; then
        val1="\"FR-$mapDep\":$pctleftprem"
        val2="\"FR-$mapDep\":$pctlefttot"

        if test -f "message/data_${clage}_1.js"; then
          echo ",$val1" >>message/data_${clage}_1.js
        else
          echo "var vacc1_${clage}Data = {" >>message/data_${clage}_1.js
          echo "$val1" >>message/data_${clage}_1.js
        fi
        if test -f "message/data_${clage}_2.js"; then
          echo ",$val2" >>message/data_${clage}_2.js
        else
          echo "var vacc2_${clage}Data = {" >>message/data_${clage}_2.js
          echo "$val2" >>message/data_${clage}_2.js
        fi
      fi

    fi

  done <"$FILE1"
  close_message
}

function special_dep() {
  oridep=$1
  mapDep=$1
  if [ "$oridep" == "971" ]; then
    mapDep="GP"
  fi
  if [ "$oridep" == "972" ]; then
    mapDep="MQ"
  fi
  if [ "$oridep" == "973" ]; then
    mapDep="GF"
  fi
  if [ "$oridep" == "974" ]; then
    mapDep="RE"
  fi
  if [ "$oridep" == "976" ]; then
    mapDep="YT"
  fi

}

rm message/information_*.txt
rm message/data_*.js

time process_all

cp message/information_*.txt ~/Desktop/website_tp/jeveuxmonvaccin/messages/
cp message/data_*.js ~/Desktop/website_tp/jeveuxmonvaccin/data/gouv/

cd message
source ~/.ovhftp
ncftpput -m -u $OVHUSER -p $OVHPIMENT -V ftp.cluster017.ovh.net /www/jeveuxmonvaccin/data/gouv ./data_*.js
echo "ftp data done"
ncftpput -m -u $OVHUSER -p $OVHPIMENT -V ftp.cluster017.ovh.net /www/jeveuxmonvaccin/messages ./information_*.txt
echo "ftp info done"
cd -
