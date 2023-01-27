package com.application.hmkcopy.presentation.home.order_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowOrderDetilItemBinding
import com.application.hmkcopy.service.response.OrdersResponse
import com.application.hmkcopy.util.extentions.SizeUtils

class OrderDetailAdapter :
    BaseListAdapter<OrdersResponse.Order, OrderDetailAdapter.OrderDetailViewHolder>(DIFF_UTIL_ITEM) {

    var onItemClick: ((OrdersResponse.Order) -> Unit)? = null

    class OrderDetailViewHolder(private val binding: RowOrderDetilItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: OrdersResponse.Order,
            size: Int,
            position: Int,
            onItemClick: ((OrdersResponse.Order) -> Unit)?
        ) {
            if (size == position + 1) {
                val rootParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(binding.root.context, 56)
            } else {
                val rootParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(binding.root.context, 0)
            }
            binding.orderDetailCardText.text = item.status?.miniSummary
            binding.orderDetailOrderDate.text = item.orderDate
            binding.orderDetailDocumentName.text = item.products?.first()?.document?.name ?: ""
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
            setCardBackgroundColor(item)
        }

        private fun setCardBackgroundColor(item: OrdersResponse.Order) {
            when (item.status?.color) {
                "red" -> {
                    binding.orderDetailCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.orderDetailCard.context,
                            R.color.red
                        )
                    )
                }
                "green" -> {
                    binding.orderDetailCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.orderDetailCard.context,
                            R.color.green
                        )
                    )
                }
                "yellow" -> {
                    binding.orderDetailCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.orderDetailCard.context,
                            R.color.yellow
                        )
                    )
                }
                "blue" -> {
                    binding.orderDetailCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.orderDetailCard.context,
                            R.color.blue
                        )
                    )
                }
            }
        }
    }


    override fun bindView(
        holder: OrderDetailViewHolder,
        position: Int,
        item: OrdersResponse.Order
    ) {
        holder.bind(item, itemCount, position, onItemClick)
    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): OrderDetailViewHolder {
        val binding = RowOrderDetilItemBinding.inflate(inflater, parent, false)
        return OrderDetailViewHolder(binding)
    }

    companion object {
        val DIFF_UTIL_ITEM = object : DiffUtil.ItemCallback<OrdersResponse.Order>() {
            override fun areItemsTheSame(
                oldItem: OrdersResponse.Order,
                newItem: OrdersResponse.Order
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: OrdersResponse.Order,
                newItem: OrdersResponse.Order
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}