#!/bin/bash

URL1=https://www.data.gouv.fr/fr/datasets/donnees-relatives-aux-personnes-vaccinees-contre-la-covid-19-1/#_

DATED=`date '+%Y%m%d'`

cp vacci-tot.csv archives/vaccination-tot-${DATED}.csv

#echo $DATED

curl -L $URL1 > vaccipage.html 
URL2=`cat vaccipage.html |grep "vacsi-tot-a-dep" |grep "<dd>"  |awk -F\" '{print $2;}'`
curl -L $URL2 > vacci-tot.csv
#echo "u2:$URL2"

