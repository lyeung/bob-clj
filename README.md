# bob

Experimental buid server in CLJ (elwood-parent reboot) 

## Installation

* Install Leiningen. (See https://leiningen.org/#install)

* Install Docker. (See https://docs.docker.com/install)

## Usage

### During development mode

Mark docker scripts as executable:

    $ chmod +x docker/*.sh

####  Running the dockerised Redis service

During testing:

    $ docker/redis-local-test.sh
    
During local development:

    $ docker/redis-local-dev.sh

#### Running the docker linked Redis CLI

During testing:

    $ docker/redis-cli-local-test.sh

During local development:

    $ docker/redis-cli-local-dev.sh
    
#### Clojure tests

To enable re-running your test codes during development, add the following line into your `~/.lein/profiles.clj`

> {:users {:plugins [[com.jakemccrary/lein-test-refresh "0.22.0"]]}}

Run `lein-test-refresh` plugin:

    $ lein test-refresh

#### Running the web server

This requires "redis-local-dev.sh".

    $ lein run

### Quick test

#### Testing "build-repo"

POSTing a payload:

    $ curl -X POST -H "Content-Type: application/json" localhost:3000/build-repo --data '{"id": "hash123", "name": "foo", "url": "bar"}'
    
Executing this CURL command should return the payload found above:
    
    $ curl -X GET -H "Content-Type: application/json" localhost:3000/build-repo/hash123
    

#### Testing "executor":

Executing this CURL command prints the root directory:

    $ curl -X POST -H "Content-Type: application/json" localhost:3000/exec/100
    
## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2018 Lenming Yeung All Rights Reseved.

Distributed under the Apache License 2.0.
