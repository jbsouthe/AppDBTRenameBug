#!/bin/bash

export JAVA_OPTS="-javaagent:/Users/josouthe/Code/AppDynamics-Java-Agents/Feb242022-v22.2.0.33545/ver22.2.0.33545/javaagent.jar -Dappdynamics.agent.applicationName=BTSplit -Dappdynamics.agent.tierName=Test -Dappdynamics.agent.nodeName=node0"

java $JAVA_OPTS -classpath BTRenameAgentPlugin-1.0.jar TestApplication 2>&1 | tee run.log
