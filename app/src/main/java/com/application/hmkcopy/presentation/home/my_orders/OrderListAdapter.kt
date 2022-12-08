package com.application.hmkcopy.presentation.home.my_orders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.data.model.OrderListItem
import com.application.hmkcopy.databinding.RowDocumentsItemBinding
import com.application.hmkcopy.util.extentions.SizeUtils
import com.application.hmkcopy.util.extentions.getDateTime

class OrderListAdapter : BaseListAdapter<OrderListItem, OrderListViewHolder>(DIFF_UTIL_CALLBACK) {
    var onItemClickListener: ((OrderListItem) -> Unit)? = null
    var onItemSelectListener: ((OrderListItem) -> Unit)? = null

    override fun bindView(holder: OrderListViewHolder, position: Int, item: OrderListItem) {
        holder.bind(
            getItem(position),
            onItemClickListener,
            itemCount,
            position,
            onItemSelectListener
        )
    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): OrderListViewHolder {
        return OrderListViewHolder(
            RowDocumentsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<OrderListItem>() {
            override fun areItemsTheSame(oldItem: OrderListItem, newItem: OrderListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: OrderListItem,
                newItem: OrderListItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class OrderListViewHolder(private val binding: RowDocumentsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        document: OrderListItem?,
        onItemClickListener: ((OrderListItem) -> Unit)?,
        size: Int,
        position: Int,
        onItemSelectListener: ((OrderListItem) -> Unit)?
    ) {
        binding.apply {
            if (size == position + 1) {
                val rootParams = root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(root.context, 56)
            }
            when(document?.isItemSelected){
                true -> {
                    root.background = ContextCompat.getDrawable(root.context, R.drawable.ic_document_item_selected);
                }
                false -> {
                    root.background = ContextCompat.getDrawable(root.context, R.drawable.ic_document_item);
                }
                else -> {}
            }
            documentItemLessonName.text = document?.lessonName
            documentsItemDocumentType.text = document?.documentType
            documentsItemPageCount.text = document?.pageSize.toString() + " Sayfa"
            documentsItemUploadDate.text = document?.uploadDate?.getDateTime()

            documentItemNextButton.setOnClickListener {
                document?.let { mDocument -> onItemClickListener?.invoke(mDocument) }
            }

            root.setOnClickListener {
                document?.let { mDocument -> onItemSelectListener?.invoke(mDocument) }
            }
        }
    }

}
