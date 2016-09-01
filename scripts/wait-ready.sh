#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/services/manage/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/peticionamento/manage/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/documents/manage/info" 30
