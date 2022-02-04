package com.kv.atmapplication.ui.main

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kv.atmapplication.MyApplication
import com.kv.atmapplication.R
import com.kv.atmapplication.databinding.ActivityMainBinding
import com.kv.atmapplication.util.hideKeyboard

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (application as MyApplication).atmRepository,
            (application as MyApplication).userRepository
        )
    }

    lateinit var binding: ActivityMainBinding
    lateinit var yourTransactionAdapter: YourTransactionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this;
        binding.viewModel = viewModel;
        yourTransactionAdapter = YourTransactionAdapter();
        binding.rvYourTransactions.adapter = yourTransactionAdapter

        viewModel.userAllTransaciton.observe(this, {
            yourTransactionAdapter.submitList(it)
        })
        viewModel.status_message.observe(this, {
            if (!TextUtils.isEmpty(it))
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        binding.tietAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    fun onClickWithdrawl(view: View) {
        viewModel.withdrawlMoney()

        hideKeyboard(this)

    }
}