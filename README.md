# bob

Experimental buid server in CLJ (elwood-parent reboot) 

## Installation

* Install Leiningen. (See https://leiningen.org/#install)

* Install Docker. (See https://docs.docker.com/install)

## Usage

### During development mode

Mark docker scripts as executable:

    $ chmod +x docker/*.sh

Run the dockerised Redis service:

    $ docker/redis-local-test.sh

Run the docker linked Redis CLI:

    $ docker/redis-cli-local-test.sh

Run the web server:

    $ lein run

Basic test:

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
