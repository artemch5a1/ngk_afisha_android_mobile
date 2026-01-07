package com.example.ngkafisha.domain.common.abstractions.utils

import java.io.File

interface FileUploader {
    suspend fun uploadFile(uploadUrl: String, file: File)
}