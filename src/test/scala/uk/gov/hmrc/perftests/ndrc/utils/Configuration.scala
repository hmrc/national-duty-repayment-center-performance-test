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

package uk.gov.hmrc.perftests.ndrc.utils

import uk.gov.hmrc.perftests.ndrc.JourneyRequests.baseUrlFor

case class Configuration(baseUrl: String, authLogin:String, ndrcLandingURL:String, timeout: Int)

object Configuration {
  
  val baseUrlNDRC = baseUrlFor("national-duty-repayment-center-service")
  val authUrl = baseUrlFor("auth-login-stub")
  val authRedirectURL = s"$baseUrlNDRC/apply-for-repayment-of-import-duty-and-import-vat/what-do-you-want-to-do"
  val authLoginStubEndpoint = "/auth-login-stub/gg-sign-in"
  val usrDir = System.getProperty("user.dir") + "/src/test/resources/data/"
 
}
