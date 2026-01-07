package com.example.ngkafisha.data.common.utils

import com.example.domain.common.abstractions.utils.FileUploader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class OkHttpFileUploader @Inject constructor(
    @Named("uploadClient") private val client: OkHttpClient
) : FileUploader {

    override suspend fun uploadFile(uploadUrl: String, file: File) = withContext(Dispatchers.IO) {

        val contentType = when (file.extension.lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "webp" -> "image/webp"
            else -> "image/*"
        }

        println("Using Content-Type: $contentType")

        val requestBody = file.asRequestBody(contentType.toMediaType())

        val request = Request.Builder()
            .url(uploadUrl)
            .put(requestBody)
            .addHeader("Content-Type", contentType)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                println("Response code: ${response.code}")
                println("Response message: ${response.message}")

                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: "No error body"
                    println("Error response body: $errorBody")
                    throw IOException("Upload failed: ${response.code} - ${response.message}. $errorBody")
                }
                println("Upload successful!")
            }
        } catch (e: IOException) {
            println("IO Exception: ${e.message}")
            throw Exception("Network error during file upload: ${e.message}", e)
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            throw Exception("Unexpected error during file upload: ${e.message}", e)
        }
    }
}