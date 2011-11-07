#! /bin/bash

# Shell script to run groovy scripts against the project artifacts

SCRIPT_HOME=$(dirname $0)


# The classpath
CP="-cp "$SCRIPT_HOME/build/classes/main
LIB=$SCRIPT_HOME/build/libs/lib
for jar in $(ls $LIB | grep '\.jar$')
do
    CP=$CP:$LIB/$jar
done

groovy $CP "$@" 
