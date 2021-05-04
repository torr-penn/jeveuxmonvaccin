#!/bin/bash 

WDIR=/work/jeveuxmonvaccin/scripts

FILE="centres-vaccination.csv"

IDX=0
DEPARTEMENT=29


FILEO="$WDIR/center/filtered_center${IDX}.csv"
cp load_center_sql.csv  prev_sql.csv
echo -n ""> load_center_sql.csv

MYDATE=`date`
	nbcenterseen=0
	nbcenterdeptseen=0
	nbcenterdeptadded=0


function process_department(){
	DEPARTEMENT=$1
	echo "process department : $DEPARTEMENT.csv"
	echo -n ""> c$DEPARTEMENT.csv
	process_file $SWDIR/$FILE
}


function process_file() {
	echo "reading file $1"
	RES=""
 	process_line < $WDIR/$FILE
	echo "total center $nbcenterseen"
	echo "total center in departement $DEPARTEMENT  :   $nbcenterdeptseen"
	echo "total center valid for departement $DEPARTEMENT  :   $nbcenterdeptadded"
	echo "file generated c$DEPARTEMENT.csv size:"
	cat c$DEPARTEMENT.csv|wc -l
}
function extra_info() {
   GID=$1

   EXTRANB=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"|wc -l`
   EXTRA1=""
   EXTRA2=""
   EXTRA3=""
   

if [ "$EXTRANB" = "1" ]; then
   EXTRA1=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"`
   EXTRA2=""
   EXTRA3=""
else

if [ "$EXTRANB" = "2" ]; then
   EXTRA1=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"|awk 'NR==1'`
   EXTRA2=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"|awk 'NR==2'`
   EXTRA3=""
else
   EXTRA1=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"|awk 'NR==1'`
   EXTRA2=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"|awk 'NR==2'`
   EXTRA3=`cat extra_$DEPARTEMENT.txt|grep -o "^$GID;.*"|awk 'NR==3'`
fi

fi

   #echo "for center : $GID I found : $EXTRA"
	
}
function output_to_file(){
					if [ "$adr_num" = "" ]; then
					  monadresse="$adr_voie"
					else 
					  monadresse="$adr_num, $adr_voie"
					fi
					if [ "$EXTRANB" = "0" ]; then
						return;
					fi
					if [ "$EXTRANB" = "1" ]; then

		      				echo -e "$nom;$monadresse;$com_nom;$com_cp;$date_ouverture;$date_fermeture;$rdv_site_web;$EXTRA1"   >> c$DEPARTEMENT.csv
						echo -e "$EXTRA1;$rdv_site_web" >> load_center_sql.csv
				        else
				               if [ "$EXTRANB" = "2" ]; then
		      			 		echo -e "$nom;$monadresse;$com_nom;$com_cp;$date_ouverture;$date_fermeture;$rdv_site_web;$EXTRA1"   >> c$DEPARTEMENT.csv
		      					echo -e "$nom;$monadresse;$com_nom;$com_cp;$date_ouverture;$date_fermeture;$rdv_site_web;$EXTRA2"   >> c$DEPARTEMENT.csv
						echo -e "$EXTRA1;$rdv_site_web" >> load_center_sql.csv
						echo -e "$EXTRA2;$rdv_site_web" >> load_center_sql.csv

						else
		      					echo -e "$nom;$monadresse;$com_nom;$com_cp;$date_ouverture;$date_fermeture;$rdv_site_web;$EXTRA1"   >> c$DEPARTEMENT.csv
		      					echo -e "$nom;$monadresse;$com_nom;$com_cp;$date_ouverture;$date_fermeture;$rdv_site_web;$EXTRA2"   >> c$DEPARTEMENT.csv
		      					echo -e "$nom;$monadresse;$com_nom;$com_cp;$date_ouverture;$date_fermeture;$rdv_site_web;$EXTRA3"   >> c$DEPARTEMENT.csv
						echo -e "$EXTRA1;$rdv_site_web" >> load_center_sql.csv
						echo -e "$EXTRA2;$rdv_site_web" >> load_center_sql.csv
						echo -e "$EXTRA3;$rdv_site_web" >> load_center_sql.csv
					     	fi
					 fi

}

MODALEXCEPTION="2426 2217 2223"
function checkModSpecial() {
	rdv_mod=$2
	PASS_MOD=""
	if [ "$rdv_mod" = "" ]; then
		PASS_MOD="Y"
	else
	if [[ $MODALEXCEPTION =~ "$1" ]];then
			PASS_MOD="Y"
	else
		PASS_MOD=""
	fi
	fi
}
OPENDATEEXCEPTION="411"
function checkOpenDateSpecial() {
	date_mod=$2
	PASS_DATE=""
	if [ "$date_mod" = "" ]; then
		if [[ "$1" =~ $OPENDATEEXCEPTION ]];then
			PASS_DATE="Y"
			date_ouverture="2021-04-15"
		else
			PASS_DATE=""
		fi
	else
		PASS_DATE="Y"
	fi
}

function process_line() {
while IFS=\; read gid nom arrete_pref_numero xy_precis id_adr adr_num adr_voie com_cp com_insee com_nom lat_coor1 long_coor1 structure_siren structure_type structure_rais structure_num structure_voie structure_cp structure_insee structure_com _userid_creation _userid_modification _edit_datemaj lieu_accessibilite rdv_lundi rdv_mardi rdv_mercredi rdv_jeudi rdv_vendredi rdv_samedi rdv_dimanche rdv date_fermeture date_ouverture rdv_site_web rdv_tel rdv_tel2 rdv_modalites rdv_consultation_prevaccination centre_svi_repondeur centre_fermeture reserve_professionels_sante
do  

        let nbcenterseen++
	RESBUFFER=""
	DEPT_CK=`echo $com_cp |sed -e 's/'${DEPARTEMENT}'[0-9][0-9][0-9]/FOUND/' `
	if [ "$DEPT_CK" = "FOUND" ]; then
       let nbcenterdeptseen++

		RESBUFFER="$com_cp;$com_nom;$nom;"
		checkOpenDateSpecial $gid $date_ouverture

		if [ "$PASS_DATE" = "Y" ]; then
			checkModSpecial $gid $rdv_modalites
			if [ "$PASS_MOD" = "Y" ]; then
	 			if [ "$rdv_site_web" != "" ]; then
					if [ "$date_fermeture" = "" ]; then
						extra_info $gid	
					if [ "$EXTRANB" = "0" ]; then
						echo "----------INCOMPLETE : $gid;$rdv_site_web;$com_nom;$com_cp" 
					fi
					output_to_file 
						RESBUFFER="$RESBUFFER;ADDED"
       					let nbcenterdeptadded++
					else
				 		viewdate=$(date -d $date_fermeture +%s)
				 		limitdate=$(date -d "+7 days"  "+%s")
			 			mylimit=$(date -d "+7 days" "+%Y-%m-%d" )

				 		if [ $limitdate -ge $viewdate ]; then
						    RESBUFFER="$RESBUFFER;FILTERED;closedate:$date_fermeture"
			          	else 
						    RESBUFFER="$RESBUFFER;ADDED"
       						let nbcenterdeptadded++
							extra_info $gid	
					if [ "$EXTRANB" = "0" ]; then
						echo "----------INCOMPLETE : $gid;$rdv_site_web;$com_nom;$com_cp" 
					fi
					output_to_file


							RESBUFFER="$com_cp;$com_nom\n$RESBUFFER"

						fi
				 	fi  
	      			else
				     RESBUFFER="$gid;$RESBUFFER;FILTERED;noweb"
				fi
			else
				RESBUFFER="$gid;$RESBUFFER;FILTERED;special : $rdv_modalites"
			fi
		else
				RESBUFFER="$gid;$RESBUFFER;FILTERED;noopendate"
		fi

		echo  "$RESBUFFER"
	fi
	structure_voie="VOIE"
	structure_num="NUM"


done

}


process_department 29
process_department 22
process_department 35
process_department 56


cd $WDIR
cat c22.csv c29.csv c35.csv c56.csv > tmp.csv
cat tmp.csv |awk -F\; '{print $1";"$2";"$3";"$4";"$5";"$6";"$8";"$10";"$11;}' >bretagne.csv
rm tmp.csv
cp bretagne.csv /tmp


diff load_center_sql.csv  prev_sql.csv
