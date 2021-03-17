package org.yakumobot.yakumochatbotNEO.networks

import okhttp3.OkHttpClient
import okhttp3.Request
import org.yakumobot.yakumochatbotNEO.BotMain

class HttpGettre {
    fun httpGet(url: String): String? {
        val body:String?
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()
        body = response.body()?.string()
        BotMain.logger.info("http return:${body}")
        return body
    }
}