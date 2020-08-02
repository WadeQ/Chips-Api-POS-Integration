package com.wadektech.chips.utils

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wadektech.chips.data.local.models.PaymentDetails
import com.wadektech.chips.ui.ChipsPaymentAdapter
import timber.log.Timber

@BindingAdapter("paymentsBindingAdapter")
fun bindPaymentDetailsAdapter(recyclerView: RecyclerView, paymentDetails: PagedList<PaymentDetails>?){
    val adapter = recyclerView.adapter as ChipsPaymentAdapter
    Timber.d("binding adapter list size is: ${paymentDetails?.size}")
    adapter.submitList(paymentDetails)
}