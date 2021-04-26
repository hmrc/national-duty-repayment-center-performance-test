/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.utils

import uk.gov.hmrc.perftests.JourneyRequests.baseUrlFor

case class Configuration(baseUrl: String, authLogin:String, cdsFileUploadLandingUrl:String, timeout: Int)

object Configuration {

  val baseUrlFileUpload = baseUrlFor("ndrc")
  val authUrl = baseUrlFor("auth-login-stub")
  val goToFileUploadPage = "/ndrc/start"
  val authRedirectURL = s"$baseUrlFileUpload/ndrc/start"
  val authLoginStubEndpoint = "/auth-login-stub/gg-sign-in"
  val usrDir = System.getProperty("user.dir") + "/src/test/resources/data/"

}
