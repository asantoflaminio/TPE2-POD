#!/bin/sh
mvn clean install
cd server
cd target
tar -xzvf tp-server-1.0-SNAPSHOT-bin.tar.gz
pwd
cd tp-server-1.0-SNAPSHOT
chmod +x run-registry.sh
chmod +x run-server.sh
cd ..
cd ..
cd ..
cd client
cd target
tar -xzvf tp-client-1.0-SNAPSHOT-bin.tar.gz
cd tp-client-1.0-SNAPSHOT
chmod +x run-client.sh
