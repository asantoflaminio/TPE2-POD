#!/bin/sh
mvn clean install
chmod +x server.sh
chmod +x query1.sh
chmod +x query2.sh
chmod +x query3.sh
chmod +x query4.sh
chmod +x query5.sh
chmod +x query6.sh
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
chmod +x query1.sh
chmod +x query2.sh
chmod +x query3.sh
chmod +x query4.sh
chmod +x query5.sh
chmod +x query6.sh
