#!/bin/bash

outputfile="id3_output.txt"

if [ -f $outputfile ] ; then
    rm $outputfile
fi

javac -d id3_decision_tree/bin -sourcepath src id3_decision_tree/src/id3_decision_tree/*.java

java -cp id3_decision_tree/bin id3_decision_tree.Driver id3_uploads/training_data.txt id3_uploads/test_data.txt > id3_output.txt 