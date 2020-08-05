package com.wadektech.chips.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wadektech.chips.R
import com.wadektech.chips.data.local.models.PaymentDetails
import com.wadektech.chips.data.local.models.TransactionDetails
import com.wadektech.chips.ui.ChipsPaymentAdapter
import com.wadektech.chips.ui.ChipsTransactionsAdapter
import timber.log.Timber



@BindingAdapter("paymentsBindingAdapter")
fun bindPaymentDetailsAdapter(recyclerView: RecyclerView, paymentDetails: PagedList<PaymentDetails>?){
    val adapter = recyclerView.adapter as ChipsPaymentAdapter
    Timber.d("binding adapter list size is: ${paymentDetails?.size}")
    adapter.submitList(paymentDetails)
}

@BindingAdapter("transactionsBindingAdapter")
fun bindTransactionsDetailsAdapter(recyclerView: RecyclerView, transactions: PagedList<TransactionDetails>?){
    val adapter = recyclerView.adapter as ChipsTransactionsAdapter
    Timber.d("binding adapter list size is: ${transactions?.size}")
    adapter.submitList(transactions)
}

@BindingAdapter("tokenImage")
fun bindPaymentTokenImages(imageView: AppCompatImageView, imageUrl: String?){
    imageUrl?.let {
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView.context)
                    .load(imageUri)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                    )
                    .into(imageView)
        }
}











