#!/bin/bash 

#https://booking.keldoc.com/api/patients/v2/timetables/81484?from=2021-05-01&to=2021-05-05&agenda_ids%5B%5D=51414&agenda_ids%5B%5D=49335
#https://partners.doctolib.fr/availabilities.json?start_date=2021-05-01&visit_motive_ids=2629459&agenda_ids=464306-439315-439318-439319-457183-440027&insurance_sector=public&practice_ids=176854&destroy_temporary=true&limit=7
#https://www.maiia.com/api/pat-public/availability-closests?centerId=60071225cd19c5464610ecfd&consultationReasonName=Seconde%20injection%20vaccin%20anti%20covid-19%20patient%20de%20%2B%20de%2060%20ans%20%28pfizer-biontech%29&from=2021-04-30T22%3A01%3A00.000Z&limit=200&page=0
link=$1

function matransform() {
	   link=$1
l2=`echo $link |sed -e 's#^.*centerId=##g'`
echo "l2 : $l2"

p1=`echo $l2 |sed -e 's#&.*##g'`
echo "p1: $p1"

#echo "p2: $p2"

#echo "p3: $p3"


echo "update vaccine_center set params='$p1' where center_id=" 
}

function find_type() {
	   link=$1

	   iskd=`echo $link |grep -o -i "^https.*keldoc.com.*" |wc -l`
       if [ "$iskd" = "1" ]; then
	       topology=1
	       return;
       fi
	   isdl=`echo $link |grep -o -i "^https.*doctolib.fr.*" |wc -l`
       if [ "$isdl" = "1" ]; then
	       topology=2
	       return;
       fi
	   isma=`echo $link |grep -o -i "^https.*maiia.com.*" |wc -l`
       if [ "$isma" = "1" ]; then
	       topology=3
	       return;
       fi

}

function dltransform() {
	   link=$1
l2=`echo $link |sed -e 's#^.*visit_motive_ids=##g'`
#echo "l2 : $l2"

p1=`echo $l2 |sed -e 's#&.*##g'`
#echo "p1: $p1"

l3=`echo $l2 |sed -e 's#^.*agenda_ids=##g'`
p2=`echo $l3 |sed -e 's#&.*##g'`
#echo "p2: $p2"

l4=`echo $l2 |sed -e 's#^.*practice_ids=##g'`
p3=`echo $l4 |sed -e 's#&.*##g'`
#echo "p3: $p3"


echo "update vaccine_center set params='$p1:$p2:$p3' where center_id=" 
}

function kdtransform() {
	   link=$1
l2=`echo $link |sed -e 's#.*/##g'`
#echo "l2 : $l2"

p1=`echo $l2 |sed -e 's#\?from.*##g'`
p2=`echo $l2 |awk -F\&agenda_ids%5B%5D= '{$1=""; print $0;}' ` 

RES=""
for x in $p2; do 
RES=$RES`echo -n $x":"`
done
RES2=`echo $RES |sed 's/.$//'`
echo "update vaccine_center set params='$p1:$RES2' where center_id=" 

}

find_type $1

       if [ "$topology" = "1" ]; then
		kdtransform $1
       fi
       if [ "$topology" = "2" ]; then
		dltransform $1
       fi
       if [ "$topology" = "3" ]; then
		matransform $1
       fi

