#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/peticionamento/manage/info" 600
./shared/scripts/wait-up.sh "https://docker:8765/documents/manage/info" 30
