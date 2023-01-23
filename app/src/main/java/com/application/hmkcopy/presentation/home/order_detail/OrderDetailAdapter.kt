package com.application.hmkcopy.presentation.home.order_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowOrderDetilItemBinding
import com.application.hmkcopy.service.response.OrdersResponse

class OrderDetailAdapter :
    BaseListAdapter<OrdersResponse.Order, OrderDetailAdapter.OrderDetailViewHolder>(DIFF_UTIL_ITEM) {

    var onItemClick: ((OrdersResponse.Order) -> Unit)? = null

    class OrderDetailViewHolder(private val binding: RowOrderDetilItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrdersResponse.Order, onItemClick: ((OrdersResponse.Order) -> Unit)?) {
            binding.orderDetailCardText.text = item.status
            binding.orderDetailOrderDate.text = item.orderDate
            binding.orderDetailDocumentName.text = "test document name"
        }

    }

    override fun bindView(holder: OrderDetailViewHolder, position: Int, item: OrdersResponse.Order) {
        holder.bind(item, onItemClick)
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
        val DIFF_UTIL_ITEM = object : DiffUtil.ItemCallback<OrdersResponse.Order>(){
            override fun areItemsTheSame(oldItem: OrdersResponse.Order, newItem: OrdersResponse.Order): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: OrdersResponse.Order, newItem: OrdersResponse.Order): Boolean {
                return oldItem == newItem
            }

        }
    }
}