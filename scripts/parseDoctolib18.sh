#!/bin/bash

cid=$1
# Finistere
FIN="brest"


#cid=${FIN}

curl -s "https://www.doctolib.fr/vaccination-covid-19/$cid?ref_visit_motive_ids[]=6970&ref_visit_motive_ids[]=7005&force_max_limit=2" > doctolib18.html
echo "https://www.doctolib.fr/vaccination-covid-19/$cid?ref_visit_motive_ids[]=6970&ref_visit_motive_ids[]=7005&force_max_limit=2"

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

#checkMotive
#checkCat

