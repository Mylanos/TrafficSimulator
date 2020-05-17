#!/bin/bash
# This is a comment!
FILE=gson-2.8.6.jar
if [[ -f "$FILE" ]]
 then
    echo "$FILE exist"
else
	wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar
fi
