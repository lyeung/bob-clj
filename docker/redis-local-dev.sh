#!/bin/bash
docker pull redis
ID=$(docker container ls --filter "name=redis-local-dev" -aq)
if [ ! -z "$ID" ] ; then
  echo "starting container: redis-local-dev"
  docker start redis-local-dev
else
  echo "initialising and starting container: redis-local-dev"
  docker run --name redis-local-dev -p 16379:6379 -d redis
fi
