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


  setup("National Duty Repayment Centre", "New Journey").withActions(
    navigateToAuthLoginStubPage,
    submitLogin,

    navigateToWhatDoYouWantToDoPage,
    chooseJourney("New"),

    navigateToImporterorRepPage,
    chooseImporRep("Representative"),
    navigateToNoEntriesPage,
    chooseNoEntries("Multiple", "4"),
    navigateToEntryAccpDatePage,
    chooseEntryAccDate("From 1 January 2021"),
    navigateToRepaymentReasonPage,
    chooseRepaymentReason("Overpayment of duty or VAT (Regulation 50)"),

//multiple spreadsheet file upload

    navigateToEnterDetailsPage,
    enterEntryDetails,
    navigateToAppRelatePage,
    chooseAppRelatetoChoice("Preference"),
    navigateToOverpaymentReasonPage,
    enterOverpaymentReason,
    navigateToReclaimPage,
    chooseReclaim("Customs Duty"),
    navigateToCustDutyPage,
    enterCustDuty,
    navigateToRepaymentAmtSummaryPage,
    navigateToSupportingDocPage,

//file upload

//Importer Details
    navigateToImpEORIPage,
    chooseEoriNoChoice("Yes"),
    navigateToEnterImpEORIPage,
    enterImpEORINo,
    navigateToImpVATPage,
    chooseVATChoice("Yes"),
    navigateToImpNamePage,
    enterImpName,
    navigateToImpAddressPage,
    enterImpPostcode,
    navigateToImpSelAddressPage,
//Representative Details
    navigateToRepEORIPage,
    chooseRepEoriNoChoice("Yes"),
    navigateToEnterRepEORIPage,
    enterRepEORINo,
    navigateToRepNamePage,
    enterRepName,
    navigateToRepAddressPage,
    enterRepPostcode,
    navigateToRepSelAddressPage,
    navigateToContactDetailsPage,
    enterContactDetails("Email","Phone"),

    navigateToDecRefNoPage,
    enterDecRefNo("Yes"),
    navigateToWhoToRepayPage,
    chooseWhoToRepay("Representative"),
    navigateToIndirectRepPage,
    chooseIndirectRepOption("No"),
//proof of authority file upload

    navigateToBankDetailsPage,
    enterBankDetails,
    navigateToCYAPage,
    postCYA,
    navigateToConfirmationPage

  )

  setup("National Duty Repayment Centre", "Amend Journey").withActions(
    navigateToAuthLoginStubPage,
    submitLogin,

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