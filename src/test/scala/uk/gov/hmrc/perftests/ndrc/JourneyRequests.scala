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
      .formParam("credentialStrength", "weak")
      .formParam("authorityId", "")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("enrolment[0].name", "HMRC-CTS-ORG")
      .formParam("enrolment[0].taxIdentifier[0].name", "EORINumber")
      .formParam("enrolment[0].taxIdentifier[0].value", "GB123456789000")
      .formParam("enrolment[0].state", "Activated")
      .check(status.is(303), status.not(404), status.not(500))
      .check(headerRegex("Location", s"${Configuration.authRedirectURL}"))
      .check(headerRegex("Set-Cookie", """mdtp=([^"]+)""").saveAs("mdtpCookie"))

  def pause = new PauseBuilder(8 seconds, None)

  def uploadWait = new PauseBuilder(12 seconds, None)

  def navigateToWhatDoYouWantToDoPage: HttpRequestBuilder = {
    http("What do you want to do? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/what-do-you-want-to-do")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseJourney(journeyChoice: String): HttpRequestBuilder = {
    http("Choose journey type")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/what-do-you-want-to-do")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$journeyChoice")
      .check(status.is(303))
  }

  def navigateToImporterorRepPage: HttpRequestBuilder = {
    http("Are you the importer or their representative? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/importer-or-representative")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseImporRep(imporrepChoice: String): HttpRequestBuilder ={
    http("Choose importer or representative")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/importer-or-representative")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$imporrepChoice")
      .check(status.is(303))
  }

  def navigateToNoEntriesPage: HttpRequestBuilder = {
    http("How many entries do you want to submit? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/how-many-entries-submitting")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseNoEntries(noOfEntriesChoice: String,enterNoEntries: String): HttpRequestBuilder ={
    http("Choose no of entries")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/how-many-entries-submitting")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$noOfEntriesChoice")
      .formParam("entries", s"$enterNoEntries")
      .check(status.is(303))
  }

  def navigateToEntryAccpDatePage: HttpRequestBuilder = {
    http("What was the entry acceptance date? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/acceptance-date-all-entries")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseEntryAccDate(entryAccDate: String): HttpRequestBuilder = {
    http("Choose entry acceptance date")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/acceptance-date-all-entries")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$entryAccDate")
      .check(status.is(303))
  }

  def navigateToRepaymentReasonPage: HttpRequestBuilder = {
    http("Why are you applying for this repayment? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/why-are-you-applying-uk")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseRepaymentReason(repaymentReasonChoice: String): HttpRequestBuilder = {
    http("Choose repayment reason")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/why-are-you-applying-uk")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$repaymentReasonChoice")
      .check(status.is(303))
  }

  def navigateToEnterDetailsPage: HttpRequestBuilder = {
    http("Enter the details of the oldest entry Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/oldest-entry-date")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterEntryDetails: HttpRequestBuilder = {
    http("Enter entry details")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/oldest-entry-date")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("EPU", "${epu}")
      .formParam("EntryNumber", "${entryNumber}")
      .formParam("EntryDate.day", "${entryDay}")
      .formParam("EntryDate.month", "${entryMonth}")
      .formParam("EntryDate.year", "${entryYear}")
      .check(status.is(303))
  }

  def navigateToAppRelatePage: HttpRequestBuilder = {
    http("What does your application relate to? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/application-reason")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseAppRelatetoChoice(apprelateChoice: String): HttpRequestBuilder = {
    http("Choose Application relate to option")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/application-reason")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[]", s"$apprelateChoice")
      .check(status.is(303))
  }

  def navigateToOverpaymentReasonPage: HttpRequestBuilder = {
    http("Tell us why the overpayment happened Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/reason-for-overpayment")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterOverpaymentReason: HttpRequestBuilder = {
    http("Reason for Overpayment details")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/reason-for-overpayment")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "shoes, jackets")
      .check(status.is(303))
  }

  def navigateToReclaimPage: HttpRequestBuilder = {
    http("What do you want to reclaim? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/reclaim")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }
//Custom Duty option
  def chooseReclaim(reclaimChoice: String): HttpRequestBuilder = {
    http("Choose Reclaim option")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/reclaim")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[]", s"$reclaimChoice")
      .check(status.is(303))
  }

  def navigateToCustDutyPage: HttpRequestBuilder = {
    http("Customs Duty overpayment Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/customs-duty-overpayment")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterCustDuty: HttpRequestBuilder = {
    http("Enter Custom duty")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/customs-duty-overpayment")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("ActualPaidAmount", "100000")
      .formParam("ShouldHavePaidAmount", "1500")
      .check(status.is(303))
  }

  def navigateToRepaymentAmtSummaryPage: HttpRequestBuilder = {
    http("Repayment amount summary Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/repayment-summary")
      .headers(headers)
      .check(status.is(200))
  }

  def navigateToSupportingDocPage: HttpRequestBuilder = {
    http("Supporting documents Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/supporting-documents")
      .headers(headers)
      .check(status.is(200))
  }
//file upload
//Importer Details
  def navigateToImpEORIPage: HttpRequestBuilder = {
    http("Does the importer have an EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/does-importer-have-eori-number")
      .headers(headers)
      .check(status.is(200))
    .check(saveCsrfToken)
  }

  def chooseEoriNoChoice(impEoriChoice: String): HttpRequestBuilder = {
    http("Choose Importer Eori no option")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/does-importer-have-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$impEoriChoice")
      .check(status.is(303))
  }

  def navigateToEnterImpEORIPage: HttpRequestBuilder = {
    http("What is the importer's EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-importer-eori-number")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterImpEORINo: HttpRequestBuilder = {
    http("Enter Importer Eori no")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-importer-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "GB123456789123000")
      .check(status.is(303))
  }

  def navigateToImpVATPage: HttpRequestBuilder = {
    http("Is the importer VAT registered? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/is-importer-vat-registered")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseVATChoice(impVATChoice: String): HttpRequestBuilder = {
    http("Choose Importer VAT no option")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/is-importer-vat-registered")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$impVATChoice")
      .check(status.is(303))
  }

  def navigateToImpNamePage: HttpRequestBuilder = {
    http("What is the importer name? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/importer-name")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterImpName: HttpRequestBuilder = {
    http("Enter Importer Name")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/importer-name")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("importerName", "Test importer")
      .check(status.is(303))
  }

  def navigateToImpAddressPage: HttpRequestBuilder = {
    http("What is the importer's address? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/select-importer-address")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }
  //Address look-up
  def enterImpPostcode: HttpRequestBuilder = {
    http("Enter Importer Postcode")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/select-importer-address")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("PostalCode", "M44TJ")
      .check(status.is(200))
  }

  def ImpSelAddressPage: HttpRequestBuilder = {
    http("Select the importer's address Page")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-importer-address")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("field-name", "{\"AddressLine1\":\"Apartment 401\",\"AddressLine2\":\"5 Ludgate Hill\",\"City\":\"Manchester\",\"CountryCode\":\"GB\",\"PostalCode\":\"M4 4TJ\"}")
      .formParam("address-postcode", "M44TJ")
      .check(status.is(303))
  }
// Representative details
  def navigateToRepEORIPage: HttpRequestBuilder = {
    http("Do you have an EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/do-you-have-an-eori-number")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def chooseRepEoriNoChoice(repEoriChoice: String): HttpRequestBuilder = {
    http("Choose Representative Eori no option")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/do-you-have-an-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", s"$repEoriChoice")
      .check(status.is(303))
  }

  def navigateToEnterRepEORIPage: HttpRequestBuilder = {
    http("What is your EORI number? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-your-eori-number")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterRepEORINo: HttpRequestBuilder = {
    http("Enter Representative Eori no")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-your-eori-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "GB123456789123")
      .check(status.is(303))
  }

  def navigateToRepNamePage: HttpRequestBuilder = {
    http("What is your name Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/your-details")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterRepName: HttpRequestBuilder = {
    http("Enter Rep Name")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/your-details")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("declarantName", "Test Declarant name")
      .formParam("agentName", "Test Representative name")
      .check(status.is(303))
  }

  def navigateToRepAddressPage: HttpRequestBuilder = {
    http("What is your business address? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/your-business-address")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }
  //Address look-up
  def enterRepPostcode: HttpRequestBuilder = {
    http("Enter Importer Postcode")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/your-business-address")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("PostalCode", "BD13ly")
      .check(status.is(200))
  }

  def RepSelAddressPage: HttpRequestBuilder = {
    http("Select your business address Page")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-agent-importer-address")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("field-name", "{\"AddressLine1\":\"36 Piccadilly\",\"City\":\"Bradford\",\"CountryCode\":\"GB\",\"PostalCode\":\"BD1 3LY\"}")
      .formParam("address-postcode", "BD13ly")
      .check(status.is(303))
  }
//Contact details
  def navigateToContactDetailsPage: HttpRequestBuilder = {
    http("How can we contact you? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/contact")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterContactDetails(email: String, phone: String): HttpRequestBuilder = {
    http("Enter Contact Details")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/contact")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[]", s"${email}")
      .formParam("email", "test@gmail.com")
      .formParam("value[]", s"${phone}")
      .formParam("phone", "09876543211")
      .check(status.is(303))
  }

  def navigateToDecRefNoPage: HttpRequestBuilder = {
    http("Do you want to create a declarant reference number? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/declarant-reference-number")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }
//Choose Yes - Declarant Ref No
  def enterDecRefNo: HttpRequestBuilder = {
    http("Enter Declarant Reference Number Details")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/declarant-reference-number")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value","01")
      .formParam("declarantReferenceNumber", "test123456")
      .check(status.is(303))
  }

  def navigateToWhoToRepayPage: HttpRequestBuilder = {
    http("Who is to be repaid? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/repaid")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }
// Choose Representative as Repaid choice
  def chooseWhoToRepay: HttpRequestBuilder = {
    http("Choose who to repaid")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/repaid")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value","02" )
      .check(status.is(303))
  }

  def navigateToIndirectRepPage: HttpRequestBuilder = {
    http("Are you an indirect representative of the importer? Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/indirect-representative")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }
//Choose No - Indirect Representative
  def chooseIndirectRepOption: HttpRequestBuilder = {
    http("Choose indirect representative")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/indirect-representative")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value","false" )
      .check(status.is(303))
  }

  def navigateToBankDetailsPage: HttpRequestBuilder = {
    http("Enter UK bank details Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-bank-details")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def enterBankDetails: HttpRequestBuilder = {
    http("Enter Bank Details")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/enter-bank-details")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .formParam("AccountName","Megacorp")
      .formParam("SortCode","207106")
      .formParam("AccountNumber","86563611")
      .check(status.is(303))
  }
//CYA
  def navigateToCYAPage: HttpRequestBuilder = {
    http("Check your answers before sending your information Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/check-your-answers")
      .headers(headers)
      .check(status.is(200))
      .check(saveCsrfToken)
  }

  def postCYA: HttpRequestBuilder = {
    http("Post Create Case journey")
      .post(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/check-your-answers")
      .headers(headers)
      .formParam("csrfToken", "${csrfToken}")
      .check(status.is(303))
  }

  def navigateToConfirmationPage: HttpRequestBuilder = {
    http("Information Sent Page")
      .get(s"${Configuration.baseUrlNDRC}/apply-for-repayment-of-import-duty-and-import-vat/confirmation")
      .headers(headers)
      .check(status.is(200))
  }

}
