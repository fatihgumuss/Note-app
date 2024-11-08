package com.example.thenotesapp.service

import android.content.Context
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiService(private val context: Context) {
    private val apiKey = "" // Replace with your Gemini API key
    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = apiKey
        )
    }
    private val TAG = "GeminiService"

    suspend fun summarizeText(text: String): String = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Starting summarization")

            val prompt = """
                Please summarize the following text in a concise way while keeping the main points and ideas. 
                Make the summary clear and easy to understand. Keep the tone consistent with the original text.
                
                Text to summarize:
                $text
            """.trimIndent()

            val response = model.generateContent(prompt)
            val summary = response.text

            Log.d(TAG, "Generated summary: $summary")
            return@withContext summary ?: "Could not generate summary."

        } catch (e: Exception) {
            Log.e(TAG, "Error during summarization", e)
            return@withContext "Error during summarization: ${e.message}"
        }
    }
}