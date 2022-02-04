package com.kv.atmapplication

import android.app.Application
import com.kv.atmapplication.db.AppDatabase
import com.kv.atmapplication.db.atm.AtmRepository
import com.kv.atmapplication.db.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getInstance(this, applicationScope) }

    val atmRepository by lazy { AtmRepository(database.atmBalanceDao()) }

    val userRepository by lazy { UserRepository(database.userBalanceDao()) }

}