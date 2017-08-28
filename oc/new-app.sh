#!/bin/bash

if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
fi

for parameter in "$@"
do
    PARAMETERS="$PARAMETERS $parameter"
    echo $PARAMETERS
done
}

getParameters(parameters) {
  PARAMETERS=''
  for parameter in parameters
  do
      PARAMETERS="$PARAMETERS $parameter"
      echo $PARAMETERS
  done
}
getParameters("$@")

echo $PARAMETERS
