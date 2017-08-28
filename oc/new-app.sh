#!/bin/bash

name=$1

if [ $# -eq 0 ]
  then
    echo "Parameters NAMESPACE, No arguments supplied"
fi

for parameter in "$@"
do
    echo "$parameter"
done

getParameters() {


}
