#!/bin/bash

sbt -DrunLocal=false -Dperftest.runSmokeTest=false gatling:test