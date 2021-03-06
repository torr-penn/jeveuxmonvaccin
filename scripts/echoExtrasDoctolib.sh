#!/bin/bash
cd /work/jeveuxmonvaccin/scripts/
echo -n "" > f1.txt
echo "### Cotes d armor " >>f1.txt
cat extra_22.txt |grep doctolib >>f1.txt
echo "####FINISTERE Manual 920 crozon - 2393 chateaulin" >> f1.txt
cat extra_29.txt |grep doctolib >> f1.txt
echo "####ILLE ET VILAINE - sauf 901 saint MALO" >>f1.txt
cat extra_35.txt |grep doctolib >> f1.txt
echo "####MORBIHAN" >> f1.txt
cat extra_56.txt |grep doctolib >> f1.txt
echo "####LOIRE ATLANTIQUE" >> f1.txt
cat extra_44.txt |grep doctolib >> f1.txt
echo "####MAINE ET LOIRE" >> f1.txt
cat extra_49.txt |grep doctolib >> f1.txt
echo "####MAYENNE" >> f1.txt
cat extra_53.txt |grep doctolib >> f1.txt
echo "####SARTHE" >> f1.txt
cat extra_72.txt |grep doctolib >> f1.txt
echo "####VENDEE" >> f1.txt
cat extra_85.txt |grep doctolib >> f1.txt

MYDIR=`pwd`
echo " now run DoctolibParser" 
WDIR=/work/torrpenn/torr-penn/target
cd $WDIR

/work/jdk18/bin/java -javaagent:/home/gtanguy/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/203.6682.168/lib/idea_rt.jar=45681:/home/gtanguy/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/203.6682.168/bin -Dfile.encoding=UTF-8 -classpath /work/jdk18/jre/lib/charsets.jar:/work/jdk18/jre/lib/deploy.jar:/work/jdk18/jre/lib/ext/cldrdata.jar:/work/jdk18/jre/lib/ext/dnsns.jar:/work/jdk18/jre/lib/ext/jaccess.jar:/work/jdk18/jre/lib/ext/jfxrt.jar:/work/jdk18/jre/lib/ext/localedata.jar:/work/jdk18/jre/lib/ext/nashorn.jar:/work/jdk18/jre/lib/ext/sunec.jar:/work/jdk18/jre/lib/ext/sunjce_provider.jar:/work/jdk18/jre/lib/ext/sunpkcs11.jar:/work/jdk18/jre/lib/ext/zipfs.jar:/work/jdk18/jre/lib/javaws.jar:/work/jdk18/jre/lib/jce.jar:/work/jdk18/jre/lib/jfr.jar:/work/jdk18/jre/lib/jfxswt.jar:/work/jdk18/jre/lib/jsse.jar:/work/jdk18/jre/lib/management-agent.jar:/work/jdk18/jre/lib/plugin.jar:/work/jdk18/jre/lib/resources.jar:/work/jdk18/jre/lib/rt.jar:/work/torrpenn/torr-penn/target/classes:/home/gtanguy/.m2/repository/com/badlogicgames/gdx/gdx-platform/1.9.10/gdx-platform-1.9.10.jar:/home/gtanguy/.m2/repository/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar:/home/gtanguy/.m2/repository/com/badlogicgames/gdx/gdx/1.9.13/gdx-1.9.13.jar:/home/gtanguy/.m2/repository/com/gtasoft/brainchess/core/1.0/core-1.0.jar:/home/gtanguy/.m2/repository/com/google/code/gson/gson/2.8.5/gson-2.8.5.jar:/home/gtanguy/.m2/repository/org/jacop/jacop/4.6.0/jacop-4.6.0.jar:/home/gtanguy/.m2/repository/org/mockito/mockito-all/1.10.19/mockito-all-1.10.19.jar:/home/gtanguy/.m2/repository/org/scala-lang/scala-library/2.12.7/scala-library-2.12.7.jar:/home/gtanguy/.m2/repository/org/scala-lang/scala-compiler/2.12.7/scala-compiler-2.12.7.jar:/home/gtanguy/.m2/repository/org/scala-lang/scala-reflect/2.12.7/scala-reflect-2.12.7.jar:/home/gtanguy/.m2/repository/org/scala-lang/modules/scala-xml_2.12/1.0.6/scala-xml_2.12-1.0.6.jar:/home/gtanguy/.m2/repository/org/slf4j/slf4j-api/1.7.24/slf4j-api-1.7.24.jar:/home/gtanguy/.m2/repository/org/slf4j/slf4j-log4j12/1.7.24/slf4j-log4j12-1.7.24.jar:/home/gtanguy/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-servlet/7.6.0.v20120127/jetty-servlet-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-security/7.6.0.v20120127/jetty-security-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-server/7.6.0.v20120127/jetty-server-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-continuation/7.6.0.v20120127/jetty-continuation-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-http/7.6.0.v20120127/jetty-http-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-io/7.6.0.v20120127/jetty-io-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/org/eclipse/jetty/jetty-util/7.6.0.v20120127/jetty-util-7.6.0.v20120127.jar:/home/gtanguy/.m2/repository/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar:/home/gtanguy/.m2/repository/commons-io/commons-io/1.3.2/commons-io-1.3.2.jar:/home/gtanguy/.m2/repository/org/slf4j/slf4j-simple/1.7.21/slf4j-simple-1.7.21.jar:/home/gtanguy/.m2/repository/org/postgresql/postgresql/42.2.18/postgresql-42.2.18.jar:/home/gtanguy/.m2/repository/org/checkerframework/checker-qual/3.5.0/checker-qual-3.5.0.jar:/home/gtanguy/.m2/repository/org/apache/commons/commons-dbcp2/2.0.1/commons-dbcp2-2.0.1.jar:/home/gtanguy/.m2/repository/org/apache/commons/commons-pool2/2.2/commons-pool2-2.2.jar:/home/gtanguy/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/home/gtanguy/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/home/gtanguy/.m2/repository/org/apache/httpcomponents/httpcore/4.4.13/httpcore-4.4.13.jar com.gtasoft.jeveuxmonvaccin.parser.DoctolibParser > $MYDIR/f2.txt 

cd -


function compare_line(){
		ORIGIN=$1

		NEW=$2
		paramO=`echo $ORIGIN |awk -F\; ' {print $5;}'`
		paramN=`echo $NEW |awk -F\; ' {print $5;}'`
		cid=`echo $NEW |awk -F\; ' {print $1;}'`
	if [ "$paramO" != "$paramN" ]; then
	if [ "$cid" != "" ]; then
		echo "update vaccine_center set params='$paramN' where center_id=$cid;"
		echo " was                              $paramO"
	fi
	fi

}
function check_files(){

	while IFS= read -r line
	do
		firstchar=`echo $line |cut -c1`
			if [ "$firstchar" != "#" ]; then
				
				centerid=`echo $line |awk -F\; ' {print $1;}'`
				line2=`cat f2.txt|grep -o "^$centerid;.*"`
			 	compare_line $line $line2
			fi


	done < "f1.txt"
}

function missing_line(){
		ORIGIN=$2
		NEW=$1
		old=$3
		paramO=`echo $ORIGIN |awk -F\; ' {print $5;}'`
		paramN=`echo $NEW |awk -F\; ' {print $5;}'`
		cid=`echo $NEW |awk -F\; ' {print $1;}'`
	if [ "$paramO" != "$paramN" ]; then
	if [ "$cid" != "" ]; then
		city=`cat positions.csv |grep -o "^$cid;.*" | awk -F\; '{print $2;}'`
		if [ "$old" == "y" ]; then
			echo "$cid =$city is missing in extra file;"
		else
			echo "$cid =$city is missing in produced file;"
		fi
	fi
	fi
}


function check_from_new(){

	while IFS= read -r line
	do
		firstchar=`echo $line |cut -c1`
			if [ "$firstchar" != "#" ]; then
				centerid=`echo $line |awk -F\; ' {print $1;}'`
				line2=`cat f1.txt|grep -o "^$centerid;.*"`
			 	missing_line $line $line2 N
			fi


	done < "f2.txt"
}
function check_from_old(){

	while IFS= read -r line
	do
		firstchar=`echo $line |cut -c1`
			if [ "$firstchar" != "#" ]; then
				centerid=`echo $line |awk -F\; ' {print $1;}'`
				line2=`cat f2.txt|grep -o "^$centerid;.*"`
			 	missing_line $line $line2 Y
			fi


	done < "f1.txt"
}
check_files
echo "################################## check from new:"
check_from_new
echo "################################## check from old:"
check_from_old

cat "f2.txt" |grep " no data center "
echo "##################################"
echo "kdiff3 f1.txt f2.txt"
