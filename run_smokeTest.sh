#!/bin/bash

sbt -DrunLocal=false -Dperftest.runSmokeTest=true Gatling/test