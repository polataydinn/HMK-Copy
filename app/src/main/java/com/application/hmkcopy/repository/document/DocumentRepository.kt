package com.application.hmkcopy.repository.document

import com.application.hmkcopy.service.Service
import com.application.hmkcopy.service.UploadService
import com.application.hmkcopy.service.request.*
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

    suspend fun createCheckoutBasket(createCheckoutBasketRequest: CreateCheckoutBasketRequest): CreateCheckoutBasketResponse {
        val response = safeApiCall {
            service.createCheckoutBasket(createCheckoutBasketRequest)
        }
        return if (response.isSuccessful) {
            response.body() ?: CreateCheckoutBasketResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Hata Olustu"
                )
            )
        } else {
            CreateCheckoutBasketResponse(apiCallError = response.castError())
        }
    }

    suspend fun setSellerPatch(sellerPatchRequest: SellerPatchRequest): SellerPatchResponse {
        val response = safeApiCall {
            service.setSellerToCheckout(sellerPatchRequest)
        }
        return if (response.isSuccessful) {
            response.body() ?: SellerPatchResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Hata Olustu"
                )
            )
        } else {
            SellerPatchResponse(apiCallError = response.castError())
        }
    }

    suspend fun checkout(): CheckoutResponse {
        val response = safeApiCall {
            service.checkout()
        }
        return if (response.isSuccessful) {
            response.body() ?: CheckoutResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Sepet al??n??rken hata olustu"
                )
            )
        } else {
            CheckoutResponse(apiCallError = response.castError())
        }
    }

    private suspend fun updateBasketInternal(
        productId: String,
        updateBasketRequest: CheckoutResponse.Checkout.Product.PrintOptions
    ): ApiCallError? {
        val response = safeApiCall {
            service.updateProduct(productId, updateBasketRequest)
        }
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }

    suspend fun updateBasket(
        productId: String,
        updateBasketRequest: CheckoutResponse.Checkout.Product.PrintOptions
    ): CheckoutResponse {
        val response = updateBasketInternal(productId, updateBasketRequest)
        return if (response != null) {
            CheckoutResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Sepet g??ncellenemedi."
                )
            )
        } else {
            checkout()
        }
    }

    suspend fun getBasketOptions(): BasketOptionsResponse {
        val response = safeApiCall {
            service.getBasketOptions()
        }
        return if (response.isSuccessful) {
            response.body() ?: BasketOptionsResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Sepet al??n??rken hata olustu"
                )
            )
        } else {
            BasketOptionsResponse(apiCallError = response.castError())
        }
    }

    suspend fun downloadDocument(documentId: String): DocumentDownloadResponse {
        val response = safeApiCall {
            service.downloadDocument(documentId)
        }
        return if (response.isSuccessful && response.body()?.presignedUrl?.isNotEmpty() == true) {
            response.body() ?: DocumentDownloadResponse(
                error = ApiCallError(
                    "101",
                    "Sepet al??n??rken hata olustu"
                )
            )
        } else {
            DocumentDownloadResponse(error = response.castError())
        }
    }


    suspend fun getPaymentWebView(): String {
        val response = safeApiCall {
            service.getPaymentWebView()
        }
        return if (response.isSuccessful) {
            response.body() ?: ""
        } else {
            ""
        }
    }

    suspend fun getOrderedItems(): OrdersResponse {
        val response = safeApiCall {
            service.getOrderedItems()
        }
        return if (response.isSuccessful) {
            response.body() ?: OrdersResponse(
                apiCallError = ApiCallError(
                    "101",
                    "Sepet al??n??rken hata olustu"
                )
            )
        } else {
            OrdersResponse(apiCallError = response.castError())
        }
    }

    suspend fun addNote(addNote: NoteRequest): ApiCallError? {
        val response = safeApiCall {
            service.addNote(addNote)
        }
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }
}