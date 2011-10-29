#!/bin/sh


CLASSPATH=`find "." -name '*.jar' |xargs echo  |tr ' ' ':'`
java -classpath $CLASSPATH org.afpparser.Main $@
