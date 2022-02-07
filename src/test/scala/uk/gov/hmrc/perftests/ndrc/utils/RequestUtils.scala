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

import io.gatling.core.Predef._
import io.gatling.core.check.CheckBuilder
import io.gatling.core.check.regex.RegexCheckType

import java.io.InputStream

trait RequestUtils {

  val CsrfPattern = """<input type="hidden" name="csrfToken" value="([^"]+)""""
  def saveCsrfToken: CheckBuilder[RegexCheckType, String, String] = regex(_ => CsrfPattern).saveAs("csrfToken")

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


  def saveSuccessRedirect: CheckBuilder[RegexCheckType, String, String] = {
    regex(successRedirectPattern).saveAs("successRedirect")
  }

  def saveErrorRedirect: CheckBuilder[RegexCheckType, String, String] = {
    regex(errorRedirectPattern).saveAs("errorRedirect")
  }

  def saveFileUploadurl: CheckBuilder[RegexCheckType, String, String] = {
    regex(amazonUrlPattern).saveAs("fileUploadAmazonUrl")
  }

  def saveCallBack: CheckBuilder[RegexCheckType, String, String] = {
    regex(callBackUrPattern).saveAs("callBack")
  }

  def saveReference: CheckBuilder[RegexCheckType, String, String] = {
    regex(referencePattern).saveAs("reference")
  }

  def saveFileType: CheckBuilder[RegexCheckType, String, String] = {
    regex(fileTypePattern).saveAs("fileType")
  }

  def saveAmazonDate: CheckBuilder[RegexCheckType, String, String] = {
    regex(amzDatePattern).saveAs("amazonDate")
  }

  def saveAmazonCredential: CheckBuilder[RegexCheckType, String, String] = {
    regex(credentialPattern).saveAs("amazonCredential")
  }

  def saveAmazonAlgorithm: CheckBuilder[RegexCheckType, String, String] = {
    regex(algorithmPattern).saveAs("amazonAlgorithm")
  }

  def saveKey: CheckBuilder[RegexCheckType, String, String] = {
    regex(keyPattern).saveAs("key")
  }

  def saveAmazonSignature: CheckBuilder[RegexCheckType, String, String] = {
    regex(signaturePattern).saveAs("amazonSignature")
  }

  def saveRequestId: CheckBuilder[RegexCheckType, String, String] = {
    regex(requestIdPattern).saveAs("requestId")
  }

  def saveUpscanIniateResponse: CheckBuilder[RegexCheckType, String, String] = {
    regex(initiateResponsePattern).saveAs("upscanInitiateResponse")
  }

  def saveUpscanInitiateRecieved: CheckBuilder[RegexCheckType, String, String] = {
    regex(initiateReceivedPattern).saveAs("upscanInitiateReceived")
  }

  def savePolicy: CheckBuilder[RegexCheckType, String, String] = {
    regex(policyPattern).saveAs("policy")
  }

  def saveAmzSessionID: CheckBuilder[RegexCheckType, String, String] = {
    regex(amazonSessionPattern).saveAs("amzSessionId")
  }

  def saveContentType: CheckBuilder[RegexCheckType, String, String] = {
    regex(contentTypePattern).saveAs("contentType")
  }

  def fileBytes(filename: String): Array[Byte] = {
    val resource: InputStream = getClass.getResourceAsStream(filename)
    Iterator.continually(resource.read).takeWhile(_ != -1).map(_.toByte).toArray
  }

}
