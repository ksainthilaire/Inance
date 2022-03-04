package com.ksainthi.inance.presentation.viewmodels

import com.ksainthi.inance.presentation.model.LoginAction
import com.ksainthi.inance.presentation.model.LoginIntent

interface IViewModel  {

    abstract fun dispatchIntent(intent: LoginIntent)
    abstract fun intentToAction(intent: LoginIntent)
    abstract fun handleAction(action: LoginAction)
}