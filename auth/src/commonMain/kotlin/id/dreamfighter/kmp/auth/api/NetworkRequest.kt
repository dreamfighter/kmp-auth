package id.dreamfighter.kmp.auth.api

import id.dreamfighter.kmp.network.model.Request
object Api{
    fun googleToken(code:String):Request {
        return Request(url = "/api/auth/google", method = "GET", path = mapOf(),query = mapOf("code" to code), body = null, requestHeaders = mapOf())
    }
    fun authGoogle(payload:Map<String, String>):Request {
        return Request(url = "/api/auth/google", method = "POST", path = mapOf(),query = mapOf(), body = payload, requestHeaders = mapOf("Content-Type" to "application/json"))
    }
}
