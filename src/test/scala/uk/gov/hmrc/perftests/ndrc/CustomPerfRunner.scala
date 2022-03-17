/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.ndrc

import io.gatling.core.Predef.{Simulation, atOnceUsers, forAll, global}
import org.slf4j.{Logger, LoggerFactory}
import uk.gov.hmrc.performance.conf.HttpConfiguration
import uk.gov.hmrc.performance.simulation.JourneySetup
import io.gatling.core.Predef._

trait CustomPerfRunner extends Simulation with HttpConfiguration with JourneySetup {
  private val logger: Logger = LoggerFactory.getLogger(classOf[CustomPerfRunner])

  def runSimulationWithUsersN(n: Int): SetUp = {
    logger.info(s"Setting up simulation ")

    if (runSingleUserJourney) {

      logger.info(
        s"'perftest.runSmokeTest' is set to true, ignoring all loads and running with only one user per journey!"
      )

      val injectedBuilders = journeys.map { scenario =>
        scenario.builder.inject(atOnceUsers(n))
      }

      setUp(injectedBuilders: _*)
        .protocols(httpProtocol)
        .assertions(global.failedRequests.count.is(0))
    } else {
      setUp(withInjectedLoad(journeys): _*)
        .protocols(httpProtocol)
        .assertions(global.failedRequests.percent.lte(percentageFailureThreshold))
        .assertions(forAll.failedRequests.percent.lte(requestPercentageFailureThreshold))
    }
  }
}
