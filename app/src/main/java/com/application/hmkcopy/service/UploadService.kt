package com.application.hmkcopy.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UploadService {
    @PUT
    suspend fun uploadDocumentToAmazon(@Url url: String, @Header("Content-Type") contentType: String, @Body document: RequestBody): Response<Unit>
}