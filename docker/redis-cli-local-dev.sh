#!/bin/bash
docker run -it --link redis-local-dev:redis --rm redis redis-cli -h redis -p 6379
