package com.kv.atmapplication.db.atm

import kotlinx.coroutines.flow.Flow

class AtmRepository(val atmBalanceDao: AtmBalanceDao) {

    val atmBalance: Flow<AtmBalance> = atmBalanceDao.getAtmBalance()

    suspend fun update(atmBalance: AtmBalance) {
        atmBalanceDao.updateUser(atmBalance)
    }
}