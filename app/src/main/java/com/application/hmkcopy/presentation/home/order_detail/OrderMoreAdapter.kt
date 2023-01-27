package com.application.hmkcopy.presentation.home.order_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowOrderedItemBinding
import com.application.hmkcopy.service.response.OrdersResponse

class OrderMoreAdapter :
    BaseListAdapter<OrdersResponse.Order.Product, OrderMoreAdapter.OrderMoreViewHolder>(
        DIFF_UTIL_ITEM
    ) {

    var onDocumentDownload: ((OrdersResponse.Order.Product) -> Unit)? = null

    inner class OrderMoreViewHolder(private val binding: RowOrderedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: OrdersResponse.Order.Product,
            onDocumentDownload: ((OrdersResponse.Order.Product) -> Unit)?
        ) {
            binding.orderStatusText.text = item.status?.summary
            binding.orderStatusDescText.text = item.status?.detail
            binding.orderDocumentName.text = item.document?.name
            binding.documentUploadDate.text = item.document?.uploadDate
            setCardBackgroundColor(item)
            setUpOrderOptions(item)
            binding.orderDownloadDocumentButton.setOnClickListener {
                onDocumentDownload?.invoke(item)
            }
        }

        private fun setUpOrderOptions(item: OrdersResponse.Order.Product) {
            binding.orderLinearContainer.apply {
                val orderNumber = OrderDetailView(context).apply {
                    name = "Sipariş numarası"
                    value = item.id ?: ""
                    isChecked = false
                }
                addView(orderNumber)

                val copyCenter = OrderDetailView(context).apply {
                    name = "Baskı merkezi"
                    value = item.seller?.name ?: ""
                    isChecked = false
                }
                addView(copyCenter)

                val pageSize = OrderDetailView(context).apply {
                    name = "Sayfa sayısı"
                    value = item.printOptions?.paperSize ?: ""
                    isChecked = false
                }
                addView(pageSize)

                if (item.printOptions?.isColored == true) {
                    val isColored = OrderDetailView(context).apply {
                        name = "Renkli baskı"
                        value = ""
                        isChecked = true
                    }
                    addView(isColored)
                }

                if (item.printOptions?.isSpiralled == true) {
                    val isSpiralled = OrderDetailView(context).apply {
                        name = "Spiral ciltleme"
                        value = ""
                        isChecked = true
                    }
                    addView(isSpiralled)
                }

                if (item.printOptions?.isCovered == true) {
                    val isCovered = OrderDetailView(context).apply {
                        name = "Karton kapak"
                        value = ""
                        isChecked = true
                    }
                    addView(isCovered)
                }

                val isCovered = OrderDetailView(context).apply {
                    name = "Toplam tutar"
                    value = "${item.prices?.price} TRY"
                    isChecked = false
                }
                addView(isCovered)
            }
        }

        private fun setCardBackgroundColor(item: OrdersResponse.Order.Product) {
            when (item.status?.color) {
                "red" -> {
                    binding.cardView3.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardView3.context, R.color.red
                        )
                    )
                }
                "green" -> {
                    binding.cardView3.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardView3.context, R.color.green
                        )
                    )
                }
                "yellow" -> {
                    binding.cardView3.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardView3.context, R.color.yellow
                        )
                    )
                }
                "blue" -> {
                    binding.cardView3.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardView3.context, R.color.blue
                        )
                    )
                }
            }
        }

    }

    companion object {
        val DIFF_UTIL_ITEM = object : DiffUtil.ItemCallback<OrdersResponse.Order.Product>() {
            override fun areItemsTheSame(
                oldItem: OrdersResponse.Order.Product, newItem: OrdersResponse.Order.Product
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: OrdersResponse.Order.Product, newItem: OrdersResponse.Order.Product
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun bindView(
        holder: OrderMoreViewHolder, position: Int, item: OrdersResponse.Order.Product
    ) {
        holder.bind(item, onDocumentDownload)
    }

    override fun createView(
        context: Context, parent: ViewGroup, inflater: LayoutInflater, viewType: Int
    ): OrderMoreViewHolder {
        return OrderMoreViewHolder(RowOrderedItemBinding.inflate(inflater, parent, false))
    }
}