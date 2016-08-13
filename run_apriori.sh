#!/bin/bash

outputfile="apriori_output.txt"

if [ -f $outputfile ] ; then
    rm $outputfile
fi

javac -d apriori_algorithm/bin -sourcepath src apriori_algorithm/src/apriori_algo/*.java

java -cp apriori_algorithm/bin apriori_algo.Driver apriori_uploads/input.txt > apriori_output.txt 
