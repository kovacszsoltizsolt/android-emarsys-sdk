#!/bin/bash

PIDS=""
RESULT=0
FIREBASE_DEVICES="--device model=flo,version=19,locale=en,orientation=portrait --device model=hammerhead,version=21,locale=en,orientation=portrait --device model=shamu,version=22,locale=en,orientation=portrait --device model=hero2lte,version=23,locale=en,orientation=portrait --device model=griffin,version=24,locale=en,orientation=portrait --device model=cruiserlteatt,version=26,locale=en,orientation=portrait --device model=sailfish,version=27,locale=en,orientation=portrait --device model=walleye,version=28,locale=en,orientation=portrait --device model=flame,version=29,locale=en,orientation=portrait"
set -ex

#Activate cloud client with the service account
gcloud auth activate-service-account -q --key-file sacc_key.json
#Set the project's id used on Google Cloud Platform
gcloud config set project "ems-mobile-sdk"

gcloud firebase test android run --type instrumentation --test "emarsys-sdk/build/outputs/apk/androidTest/debug/emarsys-sdk-debug-androidTest.apk" --app=sample/build/outputs/apk/androidTest/debug/sample-debug-androidTest.apk "$FIREBASE_DEVICES" --timeout 30m --quiet --project ems-mobile-sdk &
PIDS="$PIDS $!"

for PID in $PIDS; do
  wait "$PID" || let "RESULT=1"
done

if [[ "$RESULT" == "1" ]]; then
  exit 1
fi
