#!/usr/bin/env bash
set -e

./scripts/shared/wait-up.sh "https://docker:8765/peticionamento/info" 600
./scripts/shared/wait-up.sh "https://docker:8765/documents/info" 30