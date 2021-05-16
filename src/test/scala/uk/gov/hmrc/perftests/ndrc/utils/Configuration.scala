/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.ndrc.utils

import uk.gov.hmrc.perftests.ndrc.JourneyRequests.baseUrlFor

case class Configuration(baseUrl: String, authLogin:String, ndrcLandingURL:String, timeout: Int)

object Configuration {

  val baseUrlNDRC = baseUrlFor("national-duty-repayment-centre")
  val authUrl = baseUrlFor("auth-login-stub")
  val authRedirectURL = s"$baseUrlNDRC/national-duty-repayment-center/what-do-you-want-to-do"
  val authLoginStubEndpoint = "/auth-login-stub/gg-sign-in"
  val usrDir = System.getProperty("user.dir") + "/src/test/resources/data/"

}
