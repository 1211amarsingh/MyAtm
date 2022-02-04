package com.kv.atmapplication.db.atm

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AtmBalanceDao {
    @Query("SELECT * FROM atm_balance_table")
    fun getAtmBalance(): Flow<AtmBalance>

    @Query("SELECT * FROM atm_balance_table ")
    fun getAtmBalance1(): AtmBalance

    @Update
    suspend fun updateUser(atmBalance: AtmBalance?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(atmBalance: AtmBalance)

    @Query("DELETE FROM atm_balance_table")
    fun deleteAll()
}