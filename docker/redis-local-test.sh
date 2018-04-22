#!/bin/bash
docker pull redis
ID=$(docker container ls --filter "name=redis-local-test" -aq)
if [ ! -z "$ID" ] ; then
  echo "starting container: redis-local-test"
  docker start redis-local-test
else
  echo "initialising and starting container: redis-local-test"
  docker run --name redis-local-test -p 16378:6379 -d redis
fi
