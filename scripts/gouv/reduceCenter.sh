#!/bin/bash
if [ "$1" != "" ]; then
	FILE1="$1"
else
	FILE1=2021-06-03-prise-rdv-par-centre.csv
fi

OUTFILE=$FILE1.bretagne.csv

echo -n > $OUTFILE

function process_all(){

	while IFS= read -r line
	do
				REG=`echo $line |awk -F\, ' {print $2;}'`
			if [ "$REG" == "BRE"  ]; then
				echo $line >> $OUTFILE 	
			fi


	done < "$FILE1"
}
process_all
