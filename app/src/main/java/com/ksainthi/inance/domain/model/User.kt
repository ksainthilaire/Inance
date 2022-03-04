package com.ksainthi.inance.domain.model

data class User(
    val id: Int,
    val username: String,
    val mail: String,
    val fullName: String,
    val picture: String,
    val country: Int
)