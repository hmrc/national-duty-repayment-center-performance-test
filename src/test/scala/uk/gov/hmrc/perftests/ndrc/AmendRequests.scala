/*
 * Copyright 2021 HM Revenue & Customs
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

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.ndrc.JourneyRequests.headers
import uk.gov.hmrc.perftests.ndrc.utils.{Configuration, RequestUtils}

object AmendRequests extends ServicesConfiguration with RequestUtils {

  def navigateToCaseRefPage: HttpRequestBuilder = {
    http("What is the reference number? page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/application-reference-number")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterCaseRefPage: HttpRequestBuilder = {
    http("Enter case reference number")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/application-reference-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "NDRC2105241644B5A8E7CL6")
      .check(status.is(303))
  }

  def navigateToAmendResponseType: HttpRequestBuilder = {
    http("What do you need to do? page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/what-do-you-need-to-do")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseAmendResponseType(): HttpRequestBuilder = {
    http("Choose amend response type")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/what-do-you-need-to-do")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      //  .formParam("value[]", "supportingDocuments")
      .formParam("value[]", "furtherInformation")
      .check(status.is(303))
  }

  def navigateToFurtherInfoPage: HttpRequestBuilder = {
    http("Give us further information page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/further-information")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterFurtherInfo: HttpRequestBuilder = {
    http("Enter further info")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/further-information")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "shoes, more jackets")
      .check(status.is(303))
  }

  //CYA
  def navigateToAmendCYA: HttpRequestBuilder = {
    http("Check your answers before sending your information page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/check-answers")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def postAmendCYA: HttpRequestBuilder = {
    http("Enter further info")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/check-answers")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
  }

  //Confirmation
  def navigateToAmendConfirmationPage: HttpRequestBuilder = {
    http("Information sent page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/amend/information-sent")
      .headers(headers)
      .check(status.is(200))
  }
}
