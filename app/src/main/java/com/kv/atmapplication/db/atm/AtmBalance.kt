package com.kv.atmapplication.db.atm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "atm_balance_table")
data class AtmBalance(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "rs_100") var rs_100: Int,
    @ColumnInfo(name = "rs_200") var rs_200: Int,
    @ColumnInfo(name = "rs_500") var rs_500: Int,
    @ColumnInfo(name = "rs_2000") var rs_2000: Int,
    @ColumnInfo(name = "updated_at") var updated_at: Date,
    @ColumnInfo(name = "created_at") val created_at: Date
) {
    fun get100Text(): String {
        return rs_100.toString()
    }

    fun get200Text(): String {
        return rs_200.toString()
    }

    fun get500Text(): String {
        return rs_500.toString()
    }

    fun get2000Text(): String {
        return rs_2000.toString()
    }
}