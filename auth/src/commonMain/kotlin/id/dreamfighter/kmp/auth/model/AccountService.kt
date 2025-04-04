package id.dreamfighter.kmp.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountService(
    val type: String,
    @SerialName("project_id")
    val projectId: String,
    @SerialName("client_email")
    val clientEmail: String,
    @SerialName("client_secret")
    val clientSecret: String,
    @SerialName("client_id")
    val clientId: String,
    @SerialName("auth_uri")
    val authUri: String,
    @SerialName("token_uri")
    val tokenUri: String,
    @SerialName("auth_provider_x509_cert_url")
    val authProviderX509CertUrl: String,
    @SerialName("client_x509_cert_url")
    val clientX509CertUrl: String,
    @SerialName("scopes")
    val scopes: String
)