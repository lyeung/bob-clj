#!/bin/bash
docker run -it --link redis-local-test:redis --rm redis redis-cli -h redis -p 6379
