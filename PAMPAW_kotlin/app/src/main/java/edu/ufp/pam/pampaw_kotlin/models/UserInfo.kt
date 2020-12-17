package edu.ufp.pam.pampaw_kotlin.models

import android.text.Editable
import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("nif") val nif: Int?,
    @SerializedName("username") val username: String?
)