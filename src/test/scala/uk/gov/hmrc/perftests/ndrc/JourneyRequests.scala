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
import io.gatling.core.action.builder.PauseBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.{HttpConfiguration, ServicesConfiguration}
import uk.gov.hmrc.perftests.ndrc.utils.{Configuration, RequestUtils}
import scala.concurrent.duration._

object JourneyRequests extends HttpConfiguration with ServicesConfiguration with RequestUtils {
//Representative multi-entry journey

  val navigateToAuthLoginStubPage =
    http("Navigate to auth login stub page")
      .get(s"${Configuration.authUrl}" + s"${Configuration.authLoginStubEndpoint}")
      .check(status.is(200))

  val submitLogin =
    http("Sign in as a user who is applying for NDRC")
      .post(s"${Configuration.authUrl}" + s"${Configuration.authLoginStubEndpoint}")
      .formParam("redirectionUrl", s"${Configuration.authRedirectURL}")
      .check(status.is(303), status.not(404), status.not(500))
      .check(headerRegex("Location", s"${Configuration.authRedirectURL}"))
      .check(headerRegex("Set-Cookie", """mdtp=([^"]+)""").saveAs("mdtpCookie"))

  def pause = new PauseBuilder(8 seconds, None)

  def uploadWait = new PauseBuilder(12 seconds, None)

  def navigateToWhatDoYouWantToDoPage: HttpRequestBuilder = {
    http("What do you want to do? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/what-do-you-want-to-do")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseJourney(journeyChoice: String): HttpRequestBuilder = {
    http("Choose journey type")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/what-do-you-want-to-do")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("newOrAmendCase", s"$journeyChoice")
      .check(status.is(303))
  }

  def navigateToImporterorRepPage: HttpRequestBuilder = {
    http("Are you the importer or their representative? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/importer-or-representative")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseImporRep(imporrepChoice: String): HttpRequestBuilder ={
    http("Choose importer or representative")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/importer-or-representative")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("imporrep", s"$imporrepChoice")
      .check(status.is(303))
  }

  def navigateToNoEntriesPage: HttpRequestBuilder = {
    http("How many entries do you want to submit? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/how-many-entries-submitting")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseNoEntries(noOfEntriesChoice: String,enterNoEntries: String): HttpRequestBuilder ={
    http("Choose no of entries")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/how-many-entries-submitting")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("noOfEntriesChoice", s"$noOfEntriesChoice")
      .formParam("enterentries", s"$enterNoEntries")
      .check(status.is(303))
  }

  def navigateToEntryAccpDatePage: HttpRequestBuilder = {
    http("What was the entry acceptance date? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/acceptance-date-all-entries")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseEntryAccDate(entryAccDate: String): HttpRequestBuilder = {
    http("Choose entry acceptance date")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/acceptance-date-all-entries")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("entryAccpDateChoice", s"$entryAccDate")
      .check(status.is(303))
  }

  def navigateToRepaymentReasonPage: HttpRequestBuilder = {
    http("Why are you applying for this repayment? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/why-are-you-applying-uk")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseRepaymentReason(repaymentReasonChoice: String): HttpRequestBuilder = {
    http("Choose repayment reason")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/why-are-you-applying-uk")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("repaymentReasonChoice", s"$repaymentReasonChoice")
      .check(status.is(303))
  }

  def navigateToEnterDetailsPage: HttpRequestBuilder = {
    http("Enter the details of the oldest entry Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/oldest-entry-date")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterEntryDetails: HttpRequestBuilder = {
    http("Enter entry details")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/oldest-entry-date")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("epu", "${randomEPU}")
      .formParam("entryNumber", "${entryNo}")
      .formParam("entryDay", "${entryDay}")
      .formParam("entryDate.month", "${entryMonth}")
      .formParam("entryDate.year", "${entryYear}")
      .check(status.is(303))
  }

  def navigateToAppRelatePage: HttpRequestBuilder = {
    http("What does your application relate to? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/application-reason")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseAppRelatetoChoice(apprelateChoice: String): HttpRequestBuilder = {
    http("Choose Application relate to option")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/application-reason")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("appRelateChoice", s"$apprelateChoice")
      .check(status.is(303))
  }

  def navigateToOverpaymentReasonPage: HttpRequestBuilder = {
    http("Tell us why the overpayment happened Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/reason-for-overpayment")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterOverpaymentReason: HttpRequestBuilder = {
    http("Reason for Overpayment details")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/reason-for-overpayment")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("reasons", "shoes, jackets")
      .check(status.is(303))
  }

  def navigateToReclaimPage: HttpRequestBuilder = {
    http("What do you want to reclaim? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/reclaim")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
//Custom Duty option
  def chooseReclaim(reclaimChoice: String): HttpRequestBuilder = {
    http("Choose Reclaim option")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/reclaim")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("reclaim", s"$reclaimChoice")
      .check(status.is(303))
  }

  def navigateToCustDutyPage: HttpRequestBuilder = {
    http("Customs Duty overpayment Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/customs-duty-overpayment")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterCustDuty: HttpRequestBuilder = {
    http("Enter Custom duty")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/customs-duty-overpayment")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("custDutyPaid", "100000")
      .formParam("custDutyShldPaid", "1500")
      .check(status.is(303))
  }

  def navigateToRepaymentAmtSummaryPage: HttpRequestBuilder = {
    http("Repayment amount summary Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/repayment-summary")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def navigateToSupportingDocPage: HttpRequestBuilder = {
    http("Supporting documents Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/evidenceSupportingDocs")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
//Importer Details
  def navigateToImpEORIPage: HttpRequestBuilder = {
    http("Does the importer have an EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/does-importer-have-eori-number")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseEoriNoChoice(impEoriChoice: String): HttpRequestBuilder = {
    http("Choose Importer Eori no option")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/does-importer-have-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("impEORINo", s"$impEoriChoice")
      .check(status.is(303))
  }

  def navigateToEnterImpEORIPage: HttpRequestBuilder = {
    http("What is the importer's EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/enter-importer-eori-number")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterImpEORINo: HttpRequestBuilder = {
    http("Enter Importer Eori no")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/enter-importer-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("impEORINo", "GB123456789123000")
      .check(status.is(303))
  }

  def navigateToImpVATPage: HttpRequestBuilder = {
    http("Is the importer VAT registered? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/is-importer-vat-registered")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseVATChoice(impVATChoice: String): HttpRequestBuilder = {
    http("Choose Importer VAT no option")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/is-importer-vat-registered")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("impVATNo", s"$impVATChoice")
      .check(status.is(303))
  }

  def navigateToImpNamePage: HttpRequestBuilder = {
    http("What is the importer's name? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/representative-importer-name")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterImpName: HttpRequestBuilder = {
    http("Enter Importer Name")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/representative-importer-name")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("impFName", "Test")
      .formParam("impLName", "Importer")
      .check(status.is(303))
  }

  def navigateToImpAddressPage: HttpRequestBuilder = {
    http("What is the importer's address? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/select-importer-address")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
  //Address look-up
  def enterImpPostcode: HttpRequestBuilder = {
    http("Enter Importer Postcode")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/select-importer-address")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("impPostcode", "m44tj")
      .check(status.is(303))
  }

  def navigateToImpSelAddressPage: HttpRequestBuilder = {
    http("Select the importer's address Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/select-importer-address")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
// Representative details
  def navigateToRepEORIPage: HttpRequestBuilder = {
    http("Do you have an EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/do-you-have-an-eori-number")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def chooseRepEoriNoChoice(repEoriChoice: String): HttpRequestBuilder = {
    http("Choose Representative Eori no option")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/do-you-have-an-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("repEORINo", s"$repEoriChoice")
      .check(status.is(303))
  }

  def navigateToEnterRepEORIPage: HttpRequestBuilder = {
    http("What is your EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/enter-your-eori-number")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterRepEORINo: HttpRequestBuilder = {
    http("Enter Representative Eori no")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/enter-your-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("repEORINo", "GB123456789123")
      .check(status.is(303))
  }

  def navigateToRepNamePage: HttpRequestBuilder = {
    http("What is your name Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/representative-agent-name")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterRepName: HttpRequestBuilder = {
    http("Enter Rep Name")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/representative-agent-name")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("repFName", "Test")
      .formParam("repLName", "Representative")
      .check(status.is(303))
  }

  def navigateToRepAddressPage: HttpRequestBuilder = {
    http("What is your business address? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/your-business-address")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
  //Address look-up
  def enterRepPostcode: HttpRequestBuilder = {
    http("Enter Importer Postcode")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/your-business-address")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("repPostcode", "BD13ly")
      .check(status.is(303))
  }

  def navigateToRepSelAddressPage: HttpRequestBuilder = {
    http("Select your business address Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/select-importer-address")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
//Contact details
  def navigateToContactDetailsPage: HttpRequestBuilder = {
    http("How can we contact you? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/contact")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterContactDetails(email: String, phone: String): HttpRequestBuilder = {
    http("Enter Contact Details")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/contact")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("email", s"${email}")
      .formParam("emailvalue", "test@gmail.com")
      .formParam("phone", s"${phone}")
      .formParam("phonevalue", "09876543211")
      .check(status.is(303))
  }

  def navigateToDecRefNoPage: HttpRequestBuilder = {
    http("Do you want to create a declarant reference number? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/declarant-reference-number")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
//Choose Yes - Declarant Ref No
  def enterDecRefNo: HttpRequestBuilder = {
    http("Enter Declarant Reference Number Details")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/declarant-reference-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("decRefChoiceYes","Yes" )
      .formParam("decRefvalue", "test123456")
      .check(status.is(303))
  }

  def navigateToWhoToRepayPage: HttpRequestBuilder = {
    http("Who is to be repaid? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/repaid")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
// Choose Representative as Repaid choice
  def chooseWhoToRepay: HttpRequestBuilder = {
    http("Choose who to repaid")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/repaid")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("RepaidChoiceRep","Representative" )
      .formParam("decRefvalue", "test123456")
      .check(status.is(303))
  }

  def navigateToIndirectRepPage: HttpRequestBuilder = {
    http("Are you an indirect representative of the importer? Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/indirect-representative")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }
//Choose No - Indirect Representative
  def chooseIndirectRepOption: HttpRequestBuilder = {
    http("Choose indirect representative")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/indirect-representative")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("indirectRepChoiceNo","No" )
      .check(status.is(303))
  }

  def navigateToBankDetailsPage: HttpRequestBuilder = {
    http("Enter UK bank details Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/enter-bank-details")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def enterBankDetails: HttpRequestBuilder = {
    http("Enter Bank Details")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/enter-bank-details")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("accName","Test account")
      .formParam("sortcode","235671")
      .formParam("accNo","9872092")
      .check(status.is(303))
  }
//CYA
  def navigateToCYAPage: HttpRequestBuilder = {
    http("Check your answers before sending your information Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/check-your-answers")
      .headers(headers)
      .check(saveCsrfToken)
      .check(status.is(200))
  }

  def postCYA: HttpRequestBuilder = {
    http("Post Create Case journey")
      .post(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/check-your-answers")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
  }

  def navigateToConfirmationPage: HttpRequestBuilder = {
    http("Information Sent Page")
      .get(s"${Configuration.baseUrlNDRC}/national-duty-repayment-center/check-your-answers")
      .headers(headers)
      .check(status.is(200))
  }

}
