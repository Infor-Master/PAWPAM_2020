package edu.ufp.pam.pampaw_kotlin.store

import android.app.Application

public class Global : Application() {
    companion object {
        @JvmField
        var token: String = ""
    }
}