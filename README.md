# http_server_java

[![Build Status](https://travis-ci.org/rickerbh/http_server_java.svg?branch=master)](https://travis-ci.org/rickerbh/http_server_java) [![Coverage Status](https://coveralls.io/repos/github/rickerbh/http_server_java/badge.svg?branch=master)](https://coveralls.io/github/rickerbh/http_server_java?branch=master)

This is a basic HTTP server that will eventually pass the spec at https://github.com/8thlight/cob_spec

## Getting Started

### Install

- Install Java SDK 1.8 from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- Install Maven: `brew install maven`

Then, clone this repo.

### Running

- `cd` to the project directory
- `mvn package` will run tests, then package the application up
- `java -jar target/http_server-1.0-SNAPSHOT.jar -d src/test/resources -p 5000` will start the server on port 5000, and mount the filesystem at `./src/test/resources` as the server root.
- `mvn test` will run tests as a standalone task
- `mvn site` will create maven docs available at `./target/site/index.html`

To browse the generated maven site via this server, `mvn package; java -jar target/http_server-1.0-SNAPSHOT.jar -d target/site/ -p 5000` and then head to [http://localhost:5000/index.html](http://localhost:5000/index.html) in your browser.


## Completion status

Check out how far through I am at the [TODO](https://github.com/rickerbh/http_server_java/blob/master/TODO.md) page.

