package com.kv.atmapplication.db.user

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserBalanceDao {

    @Query("SELECT * FROM user_balance_table WHERE uid =:uid ORDER BY id DESC LIMIT 1")
    fun getUserLastTransaction(uid: Int): Flow<UserBalance>

    @Query("SELECT * FROM user_balance_table WHERE uid =:userId AND debit_amount > 0 ORDER BY id ASC")
    fun getAllUserTransactions(userId: Int): Flow<List<UserBalance>>

    @Update
    suspend fun updateUser(userBalance: UserBalance)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userBalance: UserBalance)

    @Query("DELETE FROM user_balance_table")
    suspend fun deleteAll()

}