#!/bin/bash

outputfile="kmeans_output.txt"

if [ -f $outputfile ] ; then
    rm $outputfile
fi

javac -d k_means_clustering/bin -sourcepath src k_means_clustering/src/k_means_clustering/*.java

java -cp k_means_clustering/bin k_means_clustering.Driver kmeans_uploads/input.txt > kmeans_output.txt 