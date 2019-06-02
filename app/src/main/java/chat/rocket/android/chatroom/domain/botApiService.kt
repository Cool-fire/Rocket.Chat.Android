package chat.rocket.android.chatroom.domain

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface botApiService{

    @POST("request_url")
    fun sendResponsetoBot(@Body response: response) : Call<ResponseBody>
}