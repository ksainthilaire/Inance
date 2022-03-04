package com.ksainthi.inance.data.mapper

import com.ksainthi.inance.data.model.UserApi
import com.ksainthi.inance.domain.model.User

object AuthMapper {


    fun UserApiToUser(user: UserApi): User = User(
        id = 0,
        username = user.username,
        fullName = "Dupont dupont",
        picture = "test",
        mail = "default@gmail.com",
        country = 0
    )
}