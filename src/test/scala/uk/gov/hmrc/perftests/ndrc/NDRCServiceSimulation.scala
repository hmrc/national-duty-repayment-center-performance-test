/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.ndrc

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.ndrc.JourneyRequests._
import uk.gov.hmrc.perftests.ndrc.AmendRequests._
import uk.gov.hmrc.perftests.ndrc.FileUploadRequests._

class NDRCServiceSimulation extends PerformanceTestRunner {

  setup("auth-login-stub", "Sign-in as a user who is enrolled for customs declarations File Upload") withRequests(navigateToAuthLoginStubPage, submitLogin)

  setup("National Duty Repayment Centre", "New Journey").withActions(

  )

  setup("National Duty Repayment Centre", "Amend Journey").withActions(
    navigateToCaseRefPage,
    enterCaseRefPage,

    navigateToAmendResponseType,
    chooseAmendResponseType,

    navigateToFurtherInfoPage,
    enterFurtherInfo,

    navigateToAmendCYA,
    postAmendCYA,
    navigateToAmendConfirmationPage

  )

  runSimulation()
}