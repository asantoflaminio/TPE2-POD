#!/bin/bash
cd server/target/tp-server-1.0-SNAPSHOT
gnome-terminal -e "./run-registry.sh" && gnome-terminal -e "./run-server.sh"
