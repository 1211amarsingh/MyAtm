package com.kv.atmapplication.ui.main

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.kv.atmapplication.db.atm.AtmBalance
import com.kv.atmapplication.db.atm.AtmRepository
import com.kv.atmapplication.db.user.UserBalance
import com.kv.atmapplication.db.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(var atmRepository: AtmRepository, var userRepository: UserRepository) :
    ViewModel() {
    var rs_withdrawl_amount = MutableLiveData("")
    var status_message = MutableLiveData("")
    var atmBalance: LiveData<AtmBalance> = atmRepository.atmBalance.asLiveData()

    var userLastTransaciton: LiveData<UserBalance> =
        userRepository.userLastTransactions.asLiveData()

    var userAllTransaciton: LiveData<List<UserBalance>> =
        userRepository.userAllTransactions.asLiveData()

    fun withdrawlMoney() {
        if (TextUtils.isEmpty(rs_withdrawl_amount.value)) {
            status_message.value = "Please enter amount"
        } else {
            val entered_amt = rs_withdrawl_amount.value?.toInt()
            val atm_bal = atmBalance.value!!
            val user_trans = userLastTransaciton.value!!

            if (entered_amt!! < 100) {
                status_message.value = "Enter valid amount"
            } else if (entered_amt % 100 > 0) {
                status_message.value = "Amount should be multiplication of 100"
            } else if (entered_amt > atm_bal.amount) {
                status_message.value = "Insufficient Fund"
            } else if (entered_amt > user_trans.balance_amount) {
                status_message.value = "Insufficient Fund in your account"
            } else {
                user_trans.id = 0;
                user_trans.credit_amount = 0;
                user_trans.debit_amount = entered_amt;
                user_trans.balance_amount =
                    user_trans.balance_amount - user_trans.debit_amount!!
                user_trans.date = Date()

                var used_2000 = 0;
                var used_500 = 0;
                var used_200 = 0;
                var used_100 = 0;
                var amount_due = entered_amt

                if (amount_due >= 2000 && atm_bal.rs_2000 > 0) {
                    used_2000 = amount_due / 2000
                    if (used_2000 > atm_bal.rs_2000) {
                        used_2000 = atm_bal.rs_2000
                    }
                    amount_due -= used_2000 * 2000
                }
                if (amount_due >= 500 && atm_bal.rs_500 > 0) {
                    used_500 = amount_due / 500
                    if (used_500 > atm_bal.rs_500) {
                        used_500 = atm_bal.rs_500
                    }
                    amount_due -= used_500 * 500
                }

                if (amount_due >= 200 && atm_bal.rs_200 > 0) {
                    used_200 = amount_due / 200
                    if (used_200 > atm_bal.rs_200) {
                        used_200 = atm_bal.rs_200
                    }
                    amount_due -= used_200 * 200
                }
                if (amount_due >= 100 && atm_bal.rs_100 > 0) {
                    used_100 = amount_due / 100
                    if (used_100 > atm_bal.rs_100) {
                        used_100 = atm_bal.rs_100
                    }
                    amount_due -= used_100 * 100
                }
                user_trans.rs_2000 = used_2000
                user_trans.rs_500 = used_500
                user_trans.rs_200 = used_200
                user_trans.rs_100 = used_100

                atm_bal.amount -= entered_amt
                atm_bal.rs_2000 -= used_2000
                atm_bal.rs_500 -= used_500
                atm_bal.rs_200 -= used_200
                atm_bal.rs_100 -= used_100
                atm_bal.updated_at = Date()

                if (amount_due > 0) {
                    status_message.value = "Insufficient Fund! System out of service"
                } else {
                    Log.e("XXX1", "" + Gson().toJson(atm_bal))
                    Log.e("XXX", "" + Gson().toJson(user_trans))
                    viewModelScope.launch(Dispatchers.IO) {

                        atmRepository.update(atm_bal)
                        userRepository.insert(user_trans)
                        withContext(Dispatchers.Main) {
                            rs_withdrawl_amount.value = ""
                            status_message.value = "Collect your money!"
                        }
                    }
                }
            }
        }
    }
}

class MainViewModelFactory(
    private val atmRepository: AtmRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(atmRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}