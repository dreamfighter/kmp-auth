package id.dreamfighter.kmp.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String = "",
    val email: String = "NONE",
    val uid:String = "",
    @SerialName("access_token")
    val accessToken:String = "",
    val address:String = "-",
    val phone:String = ""
)