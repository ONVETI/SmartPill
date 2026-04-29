package uz.onveti.smartpill.data.ai

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenAiResponseRequest(
    val model: String,
    val instructions: String,
    val input: String,
    val temperature: Double = 0.2,
    @SerialName("max_output_tokens")
    val maxOutputTokens: Int = 450,
)

@Serializable
data class OpenAiResponse(
    val output: List<OpenAiOutputItem> = emptyList(),
)

@Serializable
data class OpenAiOutputItem(
    val content: List<OpenAiContentItem> = emptyList(),
)

@Serializable
data class OpenAiContentItem(
    val type: String = "",
    val text: String = "",
)

@Serializable
data class OpenAiErrorResponse(
    val error: OpenAiError? = null,
)

@Serializable
data class OpenAiError(
    val message: String = "",
)
