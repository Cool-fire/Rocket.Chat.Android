package chat.rocket.android.chatroom.domain

import com.google.gson.annotations.SerializedName

data class response(@SerializedName("value")
                    val value: String,
                    @SerializedName("type")
                    val type: String)