#!/bin/bash

cid=$1
# 1737 
RETI=60071225cd19c5464610ecfd 

# 929 
LAND=600599c2e4e80549d8cb9eaa

# 1275
LESN=600686103b987b202a404143
cid=$RETI

curl -s "https://www.maiia.com/api/pat-public/consultation-reason-hcd?rootCenterId=$cid&limit=200&page=0"|python3.8 -m json.tool --sort-keys > maiiatmp.json

i=0
step=0
while IFS= read -r line
do
	((i=i+1))
	if [ "$step" = "0" ];then
		FOUNDSTEP1NB=`echo $line |egrep "\"items\":" |wc -l`
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
			exit 0
		fi

	fi
	  
done < "maiiatmp.json"
