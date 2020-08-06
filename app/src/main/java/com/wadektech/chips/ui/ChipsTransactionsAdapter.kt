package com.wadektech.chips.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wadektech.chips.data.local.models.TransactionDetails
import com.wadektech.chips.databinding.TransactionsDetailsListBinding

class ChipsTransactionsAdapter : PagedListAdapter<TransactionDetails, ChipsTransactionsAdapter.ViewHolder>(TransactionDetailsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transactionDetails = getItem(position)
        if (transactionDetails != null){
            holder.bind(transactionDetails)
        }
    }

    class ViewHolder private constructor(private val binding: TransactionsDetailsListBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionDetails: TransactionDetails){
            binding.transactions = transactionDetails
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TransactionsDetailsListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class TransactionDetailsDiffUtil : DiffUtil.ItemCallback<TransactionDetails>(){
        override fun areItemsTheSame(oldItem: TransactionDetails, newItem: TransactionDetails): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TransactionDetails, newItem: TransactionDetails): Boolean {
            return oldItem.id == newItem.id
        }
    }
}