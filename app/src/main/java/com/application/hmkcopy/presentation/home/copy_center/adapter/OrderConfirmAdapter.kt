package com.application.hmkcopy.presentation.home.copy_center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowOrderConfirmItemBinding
import com.application.hmkcopy.presentation.home.copy_center.PriceListView
import com.application.hmkcopy.presentation.home.copy_center.ProductListItem

class OrderConfirmAdapter :
    BaseListAdapter<ProductListItem, OrderConfirmAdapter.OrderConfirmViewHolder>(
        PrintListAdapter.DiffItem
    ) {
    inner class OrderConfirmViewHolder(private val binding: RowOrderConfirmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductListItem) {
            binding.orderConfirmDocumentName.text = item.name
            binding.printSettingsLinearContainer.setUpPrices(item)
        }
    }

    override fun bindView(holder: OrderConfirmViewHolder, position: Int, item: ProductListItem) {
        holder.bind(item)
    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): OrderConfirmViewHolder {
        val binding = RowOrderConfirmItemBinding.inflate(inflater, parent, false)
        return OrderConfirmViewHolder(binding)
    }

    private fun LinearLayout.setUpPrices(item: ProductListItem) {
        removeAllViews()
        item.priceInfoText.forEachIndexed { index, item ->
            addView(PriceListView(context).apply {
                if (index == 0) {
                    orderName = item.name
                    orderPrice = item.price.toString() + " TRY"
                } else {
                    orderName = item.name
                    orderPrice = "+${item.price} TRY"
                }

            })
        }
    }
}