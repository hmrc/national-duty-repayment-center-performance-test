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

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration
import uk.gov.hmrc.perftests.ndrc.utils.RequestUtils

object FileUploadRequests extends ServicesConfiguration with RequestUtils {

  // multiple & proof of authority file upload page

  def postFileInfo(fileName: String, fileType: String): HttpRequestBuilder = if (runLocal) {
    postFileInfoLocal(s"$fileName", s"$fileType")
  } else {
    postFileInfoFull(s"$fileName", s"$fileType")
  }

  // file-upload=
  def postFileInfoLocal(fileName: String, fileType: String): HttpRequestBuilder =
    http("Upload file")
      .post("${fileUploadAmazonUrl}")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
      .asMultipartForm
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "${key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
      .bodyPart(StringBodyPart("Content-Type", "${contentType}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "national-duty-repayment-center-frontend"))
      .bodyPart(StringBodyPart("policy", "${policy}"))
      .bodyPart(StringBodyPart("x-amz-meta-original-filename", s"$fileName"))
      .bodyPart(RawFileBodyPart("file", "data/" + s"$fileName").contentType(s"$fileType"))
      .check(status.is(303))
      .check(header("Location").saveAs("UpscanResponseSuccess"))

  def postFileInfoFull(fileName: String, fileType: String): HttpRequestBuilder =
    http("Upload file")
      .post("${fileUploadAmazonUrl}")
      .header("content-type", "multipart/form-data; boundary=----WebKitFormBoundarycQF5VGEC89D5MB5B")
      .asMultipartForm
      .bodyPart(StringBodyPart("x-amz-meta-callback-url", "${callBack}"))
      .bodyPart(StringBodyPart("x-amz-date", "${amazonDate}"))
      .bodyPart(StringBodyPart("success_action_redirect", "${successRedirect}"))
      .bodyPart(StringBodyPart("x-amz-credential", "${amazonCredential}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-response", "${upscanInitiateResponse}"))
      .bodyPart(StringBodyPart("x-amz-meta-upscan-initiate-received", "${upscanInitiateReceived}"))
      .bodyPart(StringBodyPart("x-amz-meta-request-id", "${requestId}"))
      .bodyPart(StringBodyPart("x-amz-meta-session-id", "${amzSessionId}"))
      .bodyPart(StringBodyPart("x-amz-meta-original-filename", s"$fileName"))
      .bodyPart(StringBodyPart("x-amz-algorithm", "${amazonAlgorithm}"))
      .bodyPart(StringBodyPart("key", "${key}"))
      .bodyPart(StringBodyPart("acl", "private"))
      .bodyPart(StringBodyPart("x-amz-signature", "${amazonSignature}"))
      .bodyPart(StringBodyPart("Content-Type", "${contentType}"))
      .bodyPart(StringBodyPart("error_action_redirect", "${errorRedirect}"))
      .bodyPart(StringBodyPart("x-amz-meta-consuming-service", "national-duty-repayment-center-frontend"))
      .bodyPart(StringBodyPart("policy", "${policy}"))
      .bodyPart(RawFileBodyPart("file", "data/" + s"$fileName").contentType(s"$fileType"))
      .check(status.is(303))
      .check(header("Location").saveAs("UpscanResponseSuccess"))
}
