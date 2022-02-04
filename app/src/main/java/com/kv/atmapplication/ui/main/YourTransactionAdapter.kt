package com.kv.atmapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kv.atmapplication.databinding.ItemYourTransBinding
import com.kv.atmapplication.db.user.UserBalance

class YourTransactionAdapter :
    ListAdapter<UserBalance, YourTransactionAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        var inflater = LayoutInflater.from(parent.context)
        val binding = ItemYourTransBinding.inflate(inflater, parent, false)

        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: YourTransactionAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }


    class ItemViewholder(var binding: ItemYourTransBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserBalance) = with(itemView) {
            binding.item = item

            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<UserBalance>() {

    override fun areItemsTheSame(oldItem: UserBalance, newItem: UserBalance): Boolean {
        return oldItem?.id == newItem?.id

    }

    override fun areContentsTheSame(oldItem: UserBalance, newItem: UserBalance): Boolean {
        return oldItem == newItem

    }
}