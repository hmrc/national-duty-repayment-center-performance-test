#!/bin/bash

sbt -DrunLocal=true -Dperftest.runSmokeTest=true Gatling/test