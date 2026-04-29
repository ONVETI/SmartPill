package uz.onveti.smartpill.data.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import uz.onveti.smartpill.BuildConfig

class OpenAiRepository(
    private val okHttpClient: OkHttpClient,
    private val json: Json,
) {

    suspend fun askMedicalQuestion(question: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.OPENAI_API_KEY
        if (apiKey.isBlank()) {
            return@withContext "AI modulni ishlatish uchun `local.properties` fayliga `OPENAI_API_KEY=...` qo'shing. API kalit gitga qo'shilmaydi."
        }

        val requestBody = json.encodeToString(
            OpenAiResponseRequest(
                model = OPENAI_MODEL,
                instructions = MEDICAL_ASSISTANT_INSTRUCTIONS,
                input = question,
            ),
        ).toRequestBody(JSON_MEDIA_TYPE)

        val request = Request.Builder()
            .url(OPENAI_RESPONSES_URL)
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            if (!response.isSuccessful) {
                val message = runCatching {
                    json.decodeFromString<OpenAiErrorResponse>(body).error?.message
                }.getOrNull()

                error(message ?: "OpenAI API javob qaytarmadi: ${response.code}")
            }


//            har bir doni ichildi yoki ichilmaganini bilish uchun firebasedan shungaki /pillStatus degan o'zgaruvchini olasan, bu parameter senga int qaytaradi lekin sen string formatda olaver va agar user dori ichsa
//            1 ichmasa 0 qaytaradi, agar user dori ichsa yani /pillStatus 1 bo'lsa insonni tuzalayotgani haqida motivational text dialog bo'lib chiqsin, agar dorini belgilangan vaqtda ichmasa ichishi zarurligi, agar
//            ahvoli kundan kunga yomonlashishi haqida dialog chiqsin.

            json.decodeFromString<OpenAiResponse>(body)
                .output
                .flatMap { item -> item.content }
                .firstOrNull { item -> item.type == "output_text" && item.text.isNotBlank() }
                ?.text
                ?.trim()
                ?: "Kechirasiz, hozir javobni o'qib bo'lmadi. Savolni qayta yuboring."
        }
    }

    private companion object {
        val JSON_MEDIA_TYPE = "application/json".toMediaType()
        const val OPENAI_RESPONSES_URL = "https://api.openai.com/v1/responses"
        const val OPENAI_MODEL = "gpt-4.1-mini"

        const val MEDICAL_ASSISTANT_INSTRUCTIONS = """
Sen SmartPill ilovasidagi tibbiy AI yordamchisan.

Qoidalar:
1. Faqat tibbiyot, dori-darmon, dori qabul qilish vaqti, dorini kech ichish oqibatlari, nojo'ya ta'sirlar, dori xavfsizligi, shifokor ko'rsatmasi va davolanish intizomi mavzularida javob ber.
2. Tibbiyotdan tashqari mavzuda javob berma. Bunday savollarda muloyim tarzda: "Men faqat dori-darmon va tibbiyotga oid savollarga yordam bera olaman" mazmunida qisqa tushuntir.
3. Aniq tashxis qo'yma, retsept yozma, doza o'zgartirishni buyurma.
4. Dorini kech ichish yoki unutish haqida so'ralsa, umumiy xavfsiz yo'l-yo'riq ber: yo'riqnoma va shifokor ko'rsatmasiga amal qilish, ikki dozani birga ichmaslik kerakligini eslatish, xavfli alomatlarda shifokor yoki tez yordamga murojaat qilish.
5. Nojo'ya ta'sirlar haqida so'ralsa, umumiy ma'lumot ber, og'ir allergiya, nafas qisishi, ko'krak og'rig'i, hushdan ketish kabi holatlarda zudlik bilan tez yordam chaqirish kerakligini ayt.
6. Javoblar o'zbek tilida, aniq, qisqa va foydalanuvchiga tushunarli bo'lsin.
7. Har bir tibbiy javob oxirida zarur bo'lsa shifokor bilan maslahatlashishni eslat.
"""
    }
}
