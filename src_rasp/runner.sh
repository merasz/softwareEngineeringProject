#!/bin/bash

mvn clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
sudo java -cp target/bleclient.jar:./lib/tinyb.jar:./target/dependencies/* at.qe.skeleton.bleclient.Main

