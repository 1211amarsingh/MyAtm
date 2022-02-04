package com.kv.atmapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kv.atmapplication.db.atm.AtmBalance
import com.kv.atmapplication.db.atm.AtmBalanceDao
import com.kv.atmapplication.db.user.UserBalance
import com.kv.atmapplication.db.user.UserBalanceDao
import com.kv.atmapplication.util.TimestampConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(
    entities = arrayOf(AtmBalance::class, UserBalance::class),
    version = 1,
    exportSchema = false
)
@TypeConverters(TimestampConverter::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun atmBalanceDao(): AtmBalanceDao
    abstract fun userBalanceDao(): UserBalanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "atm_room_db"
                ).addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    class AppDatabaseCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var atmBalanceDao = database.atmBalanceDao()
                    var userBalanceDao = database.userBalanceDao()

                    if (atmBalanceDao.getAtmBalance1() == null) {
                        val atmBalance = AtmBalance(0, 0, 0, 0, 0, 0, Date(), Date())
                        val user = UserBalance(
                            0,
                            1,
                            50000,
                            0,
                            50000,
                            0,
                            0,
                            0,
                            0,
                            Date()
                        )
                        atmBalanceDao.insert(atmBalance)
                        userBalanceDao.insert(
                            user
                        )
                    } else {

                    }
                }
            }
        }
    }
}