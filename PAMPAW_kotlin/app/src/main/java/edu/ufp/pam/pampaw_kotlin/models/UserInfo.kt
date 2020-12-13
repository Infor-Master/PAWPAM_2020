package edu.ufp.pam.pampaw_kotlin.models

import android.text.Editable
import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("name") val name: Editable,
    @SerializedName("password") val password: Editable,
    @SerializedName("nif") val nif: Editable,
    @SerializedName("username") val username: Editable
)