package chat.rocket.android.chatroom.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object botClient {
    val BASE_URL = "https://bot.serveo.net/"
    private var retrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }

}