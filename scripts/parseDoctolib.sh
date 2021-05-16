#!/bin/bash

cid=$1
# Vannes kerscao 2223 
C2223="centre-de-vaccination-covid-19-vannes-kercado"

#concarneau melgven
C1154="centre-de-vaccination-covid-19-de-concarneau"

# bigouden 1277
C1277="centre-de-vaccination-covid-19-bigouden"

#stmalo
C901="centre-de-vaccination-covid-centre-de-vaccination-du-naye"


# HIA Brest centre-de-vaccination-hia-brest 2426
C2426="centre-de-vaccination-hia-brest"


#cid=${C1277}

curl -s "https://www.doctolib.fr/booking/$cid.json"|python3.8 -m json.tool --sort-keys > doctolibtmp.json
echo "https://www.doctolib.fr/booking/$cid.json"

i=0
step=0

function checkCat(){

step=0
i=0
while IFS= read -r line
do
	((i=i+1))
	if [ "$step" = "0" ];then
		FOUNDSTEP1NB=`echo $line |egrep "\"visit_motive_categories\": \[" |wc -l`
		if [ "$FOUNDSTEP1NB" = "1" ]; then
		 step=1
		 echo "found step 1 at $i"
		 fid=0
		 fname=0
		fi
	fi
	if [ "$step" = "1" ];then
		FOUNDID=`echo $line |egrep "\"id\": " |wc -l`
		if [ "$FOUNDID" = "1" ]; then
		 echo "$line"
		 fid=1
		fi
		FOUNDNAME=`echo $line |egrep "\"name\": " |wc -l`
		if [ "$FOUNDNAME" = "1" ]; then
		 echo "$line"
		 fname=1
		fi
		if [ "$fid" = "1" ]; then
		if [ "$fname" = "1" ]; then
			echo "-------------------------------"
		fid=0
		fname=0
		fi
		fi
		FOUNDEND=`echo $line |egrep "]," |wc -l`
		if [ "$FOUNDEND" = "1" ]; then
		  step=3;	
		fi
	fi
done < "doctolibtmp.json"

}

function checkMotive(){
step=0
i=0
while IFS= read -r line
do
	((i=i+1))
	if [ "$step" = "0" ];then
		FOUNDSTEP1NB=`echo $line |egrep "\"visit_motives\": \[" |wc -l`
		if [ "$FOUNDSTEP1NB" = "1" ]; then
		 step=1
		 echo "found step 1 at $i"
		 fid=0
		 fname=0
		fi
	fi
	if [ "$step" = "1" ];then
		FOUNDID=`echo $line |egrep "\"id\": " |wc -l`
		if [ "$FOUNDID" = "1" ]; then
		 echo "$line"
		 fid=1
		fi
		FOUNDNAME=`echo $line |egrep "\"name\": " |wc -l`
		if [ "$FOUNDNAME" = "1" ]; then
		 echo "$line"
		 fname=1
		fi
		if [ "$fid" = "1" ]; then
		if [ "$fname" = "1" ]; then
			echo "-------------------------------"
		fid=0
		fname=0
		fi
		fi
		FOUNDEND=`echo $line |egrep "]," |wc -l`
		if [ "$FOUNDEND" = "1" ]; then
			step=3	
		fi

	fi
	  
done < "doctolibtmp.json"
}

checkMotive
checkCat

