#!/usr/bin/env bash
set -e



./shared/scripts/wait-up.sh "https://$1:8765/manage/info" 300
./shared/scripts/wait-up.sh "https://$1:8765/discovery/manage/info" 300
./shared/scripts/wait-up.sh "https://$1:8765/identidades/manage/info" 300