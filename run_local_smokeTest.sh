#!/bin/bash

sbt -DrunLocal=true -Dperftest.runSmokeTest=true gatling:test