package com.application.hmkcopy.repository.document

import com.application.hmkcopy.service.Service
import com.application.hmkcopy.service.UploadService
import com.application.hmkcopy.service.request.UploadDocumentRequest
import com.application.hmkcopy.service.request.UploadVerifyRequest
import com.application.hmkcopy.service.response.*
import com.application.hmkcopy.util.extentions.castError
import com.application.hmkcopy.util.extentions.safeApiCall
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class DocumentRepository @Inject constructor(
    private val service: Service,
    private val uploadService: UploadService
) {
    suspend fun uploadDocument(name: String): UploadDocumentResponse {
        val response = safeApiCall {
            service.documentUpload(
                UploadDocumentRequest(
                    name = name
                )
            )
        }
        return if (response.isSuccessful) {
            response.body() ?: UploadDocumentResponse(
                error = ApiCallError(
                    "101",
                    "Dosya Yuklenirken Hata Olustu"
                )
            )
        } else {
            UploadDocumentResponse(error = response.castError())
        }
    }

    suspend fun uploadImageWithNewUrl(url: String, part: ByteArray): Response<Unit> {
        val response = safeApiCall {
            uploadService.uploadDocumentToAmazon(
                url = url,
                contentType = "application/pdf",
                document = RequestBody.create("application/pdf".toMediaTypeOrNull(), part)
            )
        }
        return response
    }

    suspend fun uploadVerify(uploadVerifyRequest: UploadVerifyRequest): UploadVerifyResponse {
        val response = safeApiCall {
            service.uploadDocumentVerify(uploadVerifyRequest)
        }
        return if (response.isSuccessful) {
            response.body() ?: UploadVerifyResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Dosya Yuklenirken Hata Olustu"
                )
            )
        } else {
            UploadVerifyResponse(apiCallError = response.castError())
        }
    }

    suspend fun getDocuments(): DocumentsResponse {
        val response = safeApiCall {
            service.getDocuments()
        }
        return if (response.isSuccessful) {
            response.body() ?: DocumentsResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Dosya Alinirken Hata Olustu"
                )
            )
        } else {
            DocumentsResponse(apiCallError = response.castError())
        }
    }

    suspend fun deleteDocument(documentId: String): ApiCallError? {
        val response = safeApiCall {
            service.deleteDocument(documentId)
        }
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }

    suspend fun getAllSellers(): SellersResponse {
        val response = safeApiCall {
            service.getAllSellers()
        }
        return if (response.isSuccessful) {
            response.body() ?: SellersResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Hata Olustu"
                )
            )
        } else {
            SellersResponse(apiCallError = response.castError())
        }
    }
}