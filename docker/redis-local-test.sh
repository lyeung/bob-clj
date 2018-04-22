#!/bin/bash
docker pull redis
docker run --name redis-local-test -p 16378:6379 -d redis
