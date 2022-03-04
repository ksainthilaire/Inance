package com.ksainthi.inance.presentation.model

sealed class Alert {

    abstract val reason: String

    data class Info(override val reason: String) : Alert()
    data class Error(override val reason: String) : Alert()
    data class Success(override val reason: String) : Alert()
}