package com.kv.atmapplication.db.user

import kotlinx.coroutines.flow.Flow

class UserRepository(val userBalanceDao: UserBalanceDao) {

    val userAllTransactions: Flow<List<UserBalance>> = userBalanceDao.getAllUserTransactions(1)
    val userLastTransactions: Flow<UserBalance> = userBalanceDao.getUserLastTransaction(1)

    suspend fun update(userBalance: UserBalance) {
        userBalanceDao.updateUser(userBalance)
    }

    suspend fun insert(userBalance: UserBalance) {
        userBalanceDao.insert(userBalance)
    }
}