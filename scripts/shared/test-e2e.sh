#!/usr/bin/env bash
set -e

MAIN_DOCKER_COMPOSE_FILE="-f docker-compose${1:-}.yml"
COMPOSE_FILES_PARAMS="$MAIN_DOCKER_COMPOSE_FILE -f compose/docker-compose.e2e.yml"

docker-compose $COMPOSE_FILES_PARAMS up -d

./scripts/wait-ready.sh
./scripts/shared/wait-up.sh "http://docker:4444/wd/hub" 30

gradle gulpTestE2E
