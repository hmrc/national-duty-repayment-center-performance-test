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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.ndrc.AmendRequests._
import uk.gov.hmrc.perftests.ndrc.JourneyRequests._

class NDRCServiceSimulation extends PerformanceTestRunner {

  setup("National Duty Repayment Centre", "New Journey").withActions(
    navigateToAuthLoginStubPage,
    submitLogin,

    navigateToWhatDoYouWantToDoPage,
    chooseJourney("01"),

    navigateToImporterOrRepPage,
    chooseImportRep("02"),
    navigateToNoEntriesPage,
    chooseNoEntries("02", "4"),
    navigateToEnterDetailsPage,
    enterEntryDetails,
    navigateToRepaymentReasonPage,
    chooseRepaymentReason("050"),

    //multiple spreadsheet file upload

    navigateToAppRelatePage,
    chooseAppRelateToChoice("05"),
    navigateToOverpaymentReasonPage,
    enterOverpaymentReason,
    navigateToReclaimPage,
    chooseReclaim("01"),
    navigateToCustDutyPage,
    enterCustDuty,
    navigateToRepaymentAmtSummaryPage,
    navigateToSupportingDocPage,

    //file upload

    //Importer Details
    navigateToImpEORIPage,
    chooseEoriNoChoice("01"),
    navigateToEnterImpEORIPage,
    enterImpEORINo,
    navigateToImpVATPage,
    chooseVATChoice("01"),
    navigateToImpNamePage,
    enterImpName,
    //    navigateToImpAddressPage,
    //    enterImpPostcode,
    //    ImpSelAddressPage,
    //Representative Details
    navigateToRepEORIPage,
    chooseRepEoriNoChoice("true"),
    navigateToEnterRepEORIPage,
    enterRepEORINo,
    navigateToRepNamePage,
    enterRepName,
    //    navigateToRepAddressPage,
    //    enterRepPostcode,
    //    RepSelAddressPage,
    navigateToContactDetailsPage,
    enterContactDetails("01", "02"),

    navigateToDecRefNoPage,
    enterDecRefNo,
    navigateToWhoToRepayPage,
    chooseWhoToRepay,
    navigateToIndirectRepPage,
    chooseIndirectRepOption,
    //proof of authority file upload

    navigateToBankDetailsPage,
    enterBankDetails,
    //    navigateToCYAPage,
    //   // postCYA,
    //  //  navigateToConfirmationPage

  )

  setup("National Duty Repayment Centre Amend Journey", "Amend Journey").withActions(
    navigateToAuthLoginStubPage,
    submitLogin,

    navigateToWhatDoYouWantToDoPage,
    chooseJourney("02"),

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
