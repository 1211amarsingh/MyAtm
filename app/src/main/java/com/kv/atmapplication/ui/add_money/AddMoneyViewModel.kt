package com.kv.atmapplication.ui.add_money

import android.text.TextUtils
import androidx.lifecycle.*
import com.kv.atmapplication.db.atm.AtmBalance
import com.kv.atmapplication.db.atm.AtmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddMoneyViewModel(val atmRepository: AtmRepository) : ViewModel() {
    var rs_100 = MutableLiveData("")
    var rs_200 = MutableLiveData("")
    var rs_500 = MutableLiveData("")
    var rs_2000 = MutableLiveData("")

    var atmBalance: LiveData<AtmBalance> = atmRepository.atmBalance.asLiveData()

    fun addMoneyToAtm(): Boolean {
        if (TextUtils.isEmpty(rs_100.value) && TextUtils.isEmpty(rs_200.value) && TextUtils.isEmpty(
                rs_500.value
            ) && TextUtils.isEmpty(
                rs_2000.value
            )
        ) {
            return false;
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                var updatemodel = atmBalance.value as AtmBalance
                updatemodel.updated_at = Date()
                var total = updatemodel.amount
                if (!TextUtils.isEmpty(rs_100.value)) {
                    var r100 = rs_100.value!!.toInt()
                    updatemodel.rs_100 += r100
                    total += (r100 * 100)
                }
                if (!TextUtils.isEmpty(rs_200.value)) {
                    var r200 = rs_200.value!!.toInt()
                    updatemodel.rs_200 += r200
                    total += (r200 * 200)
                }
                if (!TextUtils.isEmpty(rs_500.value)) {
                    var r500 = rs_500.value!!.toInt()
                    updatemodel.rs_500 += r500
                    total += (r500 * 500)
                }
                if (!TextUtils.isEmpty(rs_2000.value)) {
                    var r2000 = rs_2000.value!!.toInt()
                    updatemodel.rs_2000 += r2000
                    total += (r2000 * 2000)
                }
                updatemodel.amount = total;
                atmRepository.update(updatemodel)
                withContext(Dispatchers.Main) {
                    rs_100.value = ""
                    rs_200.value = ""
                    rs_500.value = ""
                    rs_2000.value = ""
                }
            }
            return true;
        }
    }
}

class AddMoneyViewModelFactory(private val repository: AtmRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMoneyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddMoneyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}