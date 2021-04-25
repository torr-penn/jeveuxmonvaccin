#!/bin/bash

URL=https://www.data.gouv.fr/fr/datasets/r/5cb21a85-b0b0-4a65-a249-806a040ec372

DATED=`date '+%Y%m%d'`

cp centres-vaccination.csv archive-centres-vaccination-${DATED}.csv

echo $DATED

curl -L $URL > centres-vaccination.csv 
