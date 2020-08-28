package com.wadektech.chips.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wadektech.chips.data.local.models.PaymentDetails
import com.wadektech.chips.databinding.PaymentDetailsListBinding

class ChipsPaymentAdapter : ListAdapter<PaymentDetails, ChipsPaymentAdapter.ViewHolder>(PaymentDetailsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paymentDetails = getItem(position)
        if (paymentDetails != null){
            holder.bind(paymentDetails)
        }
    }

    class ViewHolder private constructor(private val binding: PaymentDetailsListBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentDetails: PaymentDetails){
            binding.payment = paymentDetails
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PaymentDetailsListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class PaymentDetailsDiffUtil : DiffUtil.ItemCallback<PaymentDetails>(){
        override fun areItemsTheSame(oldItem: PaymentDetails, newItem: PaymentDetails): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PaymentDetails, newItem: PaymentDetails): Boolean {
            return oldItem.tokenId == newItem.tokenId
        }
    }
}