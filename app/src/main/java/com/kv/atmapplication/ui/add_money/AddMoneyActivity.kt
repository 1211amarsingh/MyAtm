package com.kv.atmapplication.ui.add_money

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kv.atmapplication.MyApplication
import com.kv.atmapplication.R
import com.kv.atmapplication.databinding.ActivityAddMoneyBinding
import com.kv.atmapplication.ui.main.MainActivity
import com.kv.atmapplication.util.hideKeyboard

class AddMoneyActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMoneyBinding
    private val viewModel: AddMoneyViewModel by viewModels {
        AddMoneyViewModelFactory((application as MyApplication).atmRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_money)
        binding.lifecycleOwner = this;
        binding.viewModel = viewModel;

        binding.tietRs100.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.tietRs200.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.tietRs500.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.tietRs2000.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    fun onClickDepositAtm(view: View) {
        if (!viewModel.addMoneyToAtm()) {
            Toast.makeText(this, "Please enter number of notes.", Toast.LENGTH_SHORT).show()
        } else {
            hideKeyboard(this)
        }
    }

    fun onClickWithdrawl(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}