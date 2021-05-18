#!/bin/bash

sbt -DrunLocal=false -Dperftest.runSmokeTest=true gatling:test