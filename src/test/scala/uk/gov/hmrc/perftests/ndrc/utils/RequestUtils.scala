/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.ndrc.utils

import java.io.InputStream
import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import io.gatling.http.check.body.{HttpBodyRegexCheckBuilder, HttpBodyRegexOfType}
import io.gatling.http.response.Response

trait RequestUtils {

  val CsrfPattern = """<input type="hidden" name="csrfToken" value="([^"]+)""""
  def saveCsrfToken = regex(_ => CsrfPattern).saveAs("csrfToken")

  private val amazonUrlPattern = """action="(.*?)""""
  private val callBackUrPattern = """name="x-amz-meta-callback-url" value="(.*?)""""
  private val amzDatePattern = """name="x-amz-date" value="(.*?)""""
  private val credentialPattern = """name="x-amz-credential" value="(.*?)""""
  private val algorithmPattern = """name="x-amz-algorithm" value="(.*?)""""
  private val keyPattern = """name="key" value="(.*?)""""
  private val signaturePattern = """name="x-amz-signature" value="(.*?)""""
  private val initiateResponsePattern = """name="x-amz-meta-upscan-initiate-response" value="(.*?)""""
  private val requestIdPattern = """name="x-amz-meta-request-id" value="(.*?)""""
  private val amazonSessionPattern = """x-amz-meta-session-id" value="(.*?)""""
  private val initiateReceivedPattern = """name="x-amz-meta-upscan-initiate-received" value="(.*?)""""
  private val policyPattern = """name="policy" value="(.*?)""""
  private val referencePattern = """data-reference="(.*?)""""
  private val fileTypePattern = """data-filetype="(.*?)""""
  private val successRedirectPattern = """name="success_action_redirect" value="(.*?)""""
  private val errorRedirectPattern = """name="error_action_redirect" value="(.*?)""""
  private val contentTypePattern = """Content-Type" value="(.*?)""""


  def saveSuccessRedirect: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(successRedirectPattern).saveAs("successRedirect")
  }

  def saveErrorRedirect: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(errorRedirectPattern).saveAs("errorRedirect")
  }

  def saveFileUploadurl: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(amazonUrlPattern).saveAs("fileUploadAmazonUrl")
  }

  def saveCallBack: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(callBackUrPattern).saveAs("callBack")
  }

  def saveReference: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(referencePattern).saveAs("reference")
  }

  def saveFileType: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(fileTypePattern).saveAs("fileType")
  }

  def saveAmazonDate: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(amzDatePattern).saveAs("amazonDate")
  }

  def saveAmazonCredential: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(credentialPattern).saveAs("amazonCredential")
  }

  def saveAmazonAlgorithm: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(algorithmPattern).saveAs("amazonAlgorithm")
  }

  def saveKey: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(keyPattern).saveAs("key")
  }

  def saveAmazonSignature: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(signaturePattern).saveAs("amazonSignature")
  }

  def saveRequestId: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(requestIdPattern).saveAs("requestId")
  }

  def saveUpscanIniateResponse: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(initiateResponsePattern).saveAs("upscanInitiateResponse")
  }

  def saveUpscanInitiateRecieved: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(initiateReceivedPattern).saveAs("upscanInitiateReceived")
  }

  def savePolicy: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(policyPattern).saveAs("policy")
  }

  def saveAmzSessionID: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(amazonSessionPattern).saveAs("amzSessionId")
  }

  def bodyCheck(body: String): HttpBodyRegexCheckBuilder[String] with HttpBodyRegexOfType = regex(body)

  def saveContentType: CheckBuilder[HttpCheck, Response, CharSequence, String] = {
    bodyCheck(contentTypePattern).saveAs("contentType")
  }

  def fileBytes(filename: String): Array[Byte] = {
    val resource: InputStream = getClass.getResourceAsStream(filename)
    Iterator.continually(resource.read).takeWhile(_ != -1).map(_.toByte).toArray
  }

}
