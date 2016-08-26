#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/manage/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/discovery/manage/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/services/manage/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/userauthentication/manage/info" 150