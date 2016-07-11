#!/usr/bin/env bash
set -e

./shared/scripts/wait-up.sh "https://docker:8765/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/discovery/info" 300
./shared/scripts/wait-up.sh "https://docker:8765/services/info" 150
./shared/scripts/wait-up.sh "https://docker:8765/userauthentication/info" 100