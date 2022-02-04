package com.kv.atmapplication.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_balance_table")
data class UserBalance(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "uid") var uid: Int = 1,
    @ColumnInfo(name = "credit_amount") var credit_amount: Int,
    @ColumnInfo(name = "debit_amount") var debit_amount: Int,
    @ColumnInfo(name = "balance_amount") var balance_amount: Int,
    @ColumnInfo(name = "rs_100") var rs_100: Int,
    @ColumnInfo(name = "rs_200") var rs_200: Int,
    @ColumnInfo(name = "rs_500") var rs_500: Int,
    @ColumnInfo(name = "rs_2000") var rs_2000: Int,
    @ColumnInfo(name = "date") var date: Date
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