#!/bin/sh
mvn clean install
cd podtpe
cd server
cd target
tar -xzvf tp-server-1.0-SNAPSHOT-bin.tar
pwd
cd tp-server-1.0-SNAPSHOT
chmod +x run-registry.sh
chmod +x run-server.sh
cd ..
cd ..
cd ..
cd client
cd target
tar -xzvf podtpe-client-1.0-SNAPSHOT-bin.tar.gz
cd podtpe-client-1.0-SNAPSHOT
chmod +x run-managementclient.sh
chmod +x run-fiscalclient.sh
chmod +x run-queryclient.sh
chmod +x run-votingclient.sh