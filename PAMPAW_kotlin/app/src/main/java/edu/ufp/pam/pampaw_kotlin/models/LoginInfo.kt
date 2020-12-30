package edu.ufp.pam.pampaw_kotlin.models

import com.google.gson.annotations.SerializedName

data class LoginInfo(
    @SerializedName("password") val password: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("token") val token: String?
)