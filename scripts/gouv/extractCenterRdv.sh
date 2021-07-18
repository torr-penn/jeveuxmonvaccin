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
			codpost=`echo $line |awk -F\; ' {print $3;}'|cut -c 1,2`

			
			echo "################## $centerid $name"
			echo -n > rdv/rdv.$centerid.csv
			if [ "$codpost" == "29" ]; then
				check_rdv $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "22" ]; then
				check_rdv $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "35" ]; then
				check_rdv $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "56" ]; then
				check_rdv $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "44" ]; then
				check_rdv_pdl $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "49" ]; then
				check_rdv_pdl $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "53" ]; then
				check_rdv_pdl $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "72" ]; then
				check_rdv_pdl $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			if [ "$codpost" == "85" ]; then
				check_rdv_pdl $centerid "$name"
				echo "################## $centerid $name -> $nb"
			fi
			
	done < "$FILE2"
}

function check_rdv_pdl(){
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
	done < "$FILE1.paysdeloire.csv"
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

