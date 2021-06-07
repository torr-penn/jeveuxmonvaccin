#!/bin/bash 
if [ "$1" != "" ]; then
	FILE1="$1"
else
	FILE1=2021-06-03-prise-rdv-par-centre.csv
fi
FILE2=positions.csv




function process_all(){

	while IFS= read -r line
	do
			centerid=`echo $line |awk -F\; ' {print $1;}'`
			name=`echo $line |awk -F\; ' {print $2;}'`
			echo "################## $centerid $name"
			echo -n > rdv/rdv.$centerid.csv
		#	if [ "$centerid" == "1303" ]; then
				check_rdv $centerid "$name"
				echo "################## $centerid $name -> $nb"
		#	fi
			
	done < "$FILE2"
}

function check_rdv(){
		name=$2
		center_id=$1

	nb=0;
	while IFS= read -r line
	do
		nline=`echo $line |awk -F'"' -v OFS='' '{ for (i=2; i<=NF; i+=2) gsub(",", "", $i) } 1' `
			cid=`echo $nline |awk -F\, ' {print $4;}'`
			if [ "$cid" == "$center_id" ]; then
						datex=`echo $nline |awk -F\, ' {print $7;}'`
						nbx=`echo $nline |awk -F\, ' {print $8;}'`
						rgx=`echo $nline |awk -F\, ' {print $6;}'`
						echo "$cid;$rgx;$datex;$nbx;" >> rdv/rdv.$cid.csv
						((nb=$nb+1))
			fi
	done < "$FILE1.bretagne.csv"
}
if test -f "$FILE1.bretagne.csv"; then
	    echo "$FILE1.bretagne.csv exists no reduction."
else
echo " first I reduce the filesize"

time 	./reduceCenter.sh $FILE1
fi
time process_all

