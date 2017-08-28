#!/bin/bash

name=$1
oc new-app $1

if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
fi

fucntion getParameters() {


}
