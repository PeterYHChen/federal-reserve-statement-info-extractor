#!/bin/bash

year=2006
month=1
filename=""

for i in 2006 2007 2008 2009 2010 2011 2012 2013 2014 2015 2016 2017

do
    for j in 1 2 3 4 5 6 7 8 9 10 11 12
    do
	if [ $j -lt 10 ] 
	then
	    filename=$i'_0'$j'.stmt'
	else
	    filename=$i'_'$j'.stmt'
	fi
    
	if [ -f $filename ]
	then
	    java HW4_V6 WSJ_02-21.pos $filename > /mnt/c/computer_science/NLP/project/federal-reserve-statement-info-extractor/statements_pos/$filename'-pos' 
	    echo $filename" executed"
	else
	   echo $filename" not found" #don't do anything  java HW4_V7 WSJ_02-21.pos 2006_01.stmt > 2006-01.stmt-pos
	fi
    done

done
