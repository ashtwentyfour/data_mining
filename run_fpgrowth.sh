#!/bin/bash

outputfile="fpgrowth_output.txt"

if [ -f $outputfile ] ; then
    rm $outputfile
fi

javac -d fp_growth/bin -sourcepath src fp_growth/src/fp_growth/*.java

java -cp fp_growth/bin fp_growth.Driver fpgrowth_uploads/input.txt > fpgrowth_output.txt 