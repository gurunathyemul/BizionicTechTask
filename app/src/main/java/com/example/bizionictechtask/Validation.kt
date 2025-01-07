package com.example.bizionictechtask

import android.util.Patterns

object Validation {

    fun String.isValidEmail(): Boolean {
        return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

}