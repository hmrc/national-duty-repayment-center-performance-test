/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import JourneyRequests._

class NDRCServiceSimulation extends PerformanceTestRunner {

  setup("home-page", "Home Page") withRequests (navigateToHomePage)

  setup("post-vat-return-period", "Post vat return period") withRequests (postVatReturnPeriod)

  setup("get-turnover-page", "Get turnover page") withRequests (getTurnoverPage)

  runSimulation()
}