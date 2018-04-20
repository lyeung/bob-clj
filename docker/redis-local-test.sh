#!/bin/bash
docker pull redis
docker run --name redis-local-test -p 6379:6379 -d redis
