package io.github.warleysr.pronki.api

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException

class GeminiAPI {

    companion object {

        suspend fun generateContent(
            apiKey: String,
            modelName: String,
            prompt: String,
            onSuccess: (String?, Int?) -> Unit,
            onFailure: (String?) -> Unit
        ) {
            val model = GenerativeModel(
                modelName = modelName,
                apiKey = apiKey,
                safetySettings = arrayListOf()
            )
            try {
                val content = model.generateContent(prompt)
                println("############# Gemini generated content #############")
                println(content.text)
                println("##################################")
                onSuccess(content.text, content.usageMetadata?.totalTokenCount)
            } catch (exception: GoogleGenerativeAIException) {
                exception.printStackTrace()
                onFailure(exception.localizedMessage)
            }
        }

        fun getAvailableModels(): Map<String, String> {
            return mapOf(
                Pair("gemini-flash-latest", "Gemini Flash (Latest)"),
                Pair("gemini-pro-latest", "Gemini Pro (Latest)"),
                Pair("gemini-flash-lite-latest", "Gemini Flash-Lite (Latest)"),
            )
        }
    }
}