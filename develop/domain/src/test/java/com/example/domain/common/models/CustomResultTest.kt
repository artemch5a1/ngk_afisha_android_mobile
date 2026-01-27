package com.example.domain.common.models

import com.example.domain.common.exceptions.ApiException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CustomResultTest {

    @Test
    fun `success should create result with value and isSuccess true`() {
        // When
        val result = CustomResult.success("test_value")

        // Then
        assertTrue(result.isSuccess)
        assertEquals("test_value", result.value)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `failure with message should create result with error message`() {
        // When
        val result = CustomResult.failure<String>("Error message")

        // Then
        assertFalse(result.isSuccess)
        assertNull(result.value)
        assertEquals("Error message", result.errorMessage)
    }

    @Test
    fun `failure with ApiException should use exception message`() {
        // Given
        val exception = ApiException(400, "Bad Request")

        // When
        val result = CustomResult.failure<String>(exception)

        // Then
        assertFalse(result.isSuccess)
        assertNull(result.value)
        assertEquals("Bad Request", result.errorMessage)
    }

    @Test
    fun `failure with generic exception should use exception message`() {
        // Given
        val exception = RuntimeException("Generic error")

        // When
        val result = CustomResult.failure<String>(exception)

        // Then
        assertFalse(result.isSuccess)
        assertNull(result.value)
        assertEquals("Generic error", result.errorMessage)
    }

    @Test
    fun `failure with ApiException and codeMessageMap should use mapped message`() {
        // Given
        val exception = ApiException(400, "Bad Request")
        val codeMessageMap = mapOf(400 to "Custom error message")

        // When
        val result = CustomResult.failure<String>(exception, codeMessageMap)

        // Then
        assertFalse(result.isSuccess)
        assertNull(result.value)
        assertEquals("Custom error message", result.errorMessage)
    }

    @Test
    fun `failure with ApiException and codeMessageMap should fallback to exception message when code not found`() {
        // Given
        val exception = ApiException(404, "Not Found")
        val codeMessageMap = mapOf(400 to "Custom error message")

        // When
        val result = CustomResult.failure<String>(exception, codeMessageMap)

        // Then
        assertFalse(result.isSuccess)
        assertEquals("Not Found", result.errorMessage)
    }

    @Test
    fun `failure with null exception message should use empty string`() {
        // Given
        val exception = Exception("")

        // When
        val result = CustomResult.failure<String>(exception)

        // Then
        assertFalse(result.isSuccess)
        assertEquals("", result.errorMessage)
    }

    @Test
    fun `success with null value should still be success`() {
        // When
        val result = CustomResult.success<String?>(null)

        // Then
        assertTrue(result.isSuccess)
        assertNull(result.value)
    }
}
