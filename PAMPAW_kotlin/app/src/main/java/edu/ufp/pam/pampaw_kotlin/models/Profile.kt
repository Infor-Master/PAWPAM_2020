package edu.ufp.pam.pampaw_kotlin.models

import com.google.gson.annotations.SerializedName

data class Profile(
  @SerializedName("ID") val id: Int?,
  @SerializedName("Name") val name: String?,
  @SerializedName("NIF") val nif: String?,
  @SerializedName("Password") val password: String?,
  @SerializedName("NewPassword") val newPassword: String?,
  @SerializedName("Username") val username: String?
  )