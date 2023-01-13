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
import com.application.hmkcopy.service.response.DocumentsResponse
import com.application.hmkcopy.util.extentions.SizeUtils
import com.application.hmkcopy.util.extentions.getDateTime

class OrderListAdapter : BaseListAdapter<Result, OrderListViewHolder>(DIFF_UTIL_CALLBACK) {
    var onItemClickListener: ((Result) -> Unit)? = null
    var onItemSelectListener: ((Result) -> Unit)? = null

    override fun bindView(holder: OrderListViewHolder, position: Int, item: Result) {
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
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class OrderListViewHolder(private val binding: RowDocumentsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        document: Result?,
        onItemClickListener: ((Result) -> Unit)?,
        size: Int,
        position: Int,
        onItemSelectListener: ((Result) -> Unit)?
    ) {
        binding.apply {
            if (size == position + 1) {
                val rootParams = root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(root.context, 56)
            } else {
                val rootParams = root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(root.context, 0)
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
            documentItemLessonName.text = document?.name?.substringBeforeLast(".")
            documentsItemDocumentType.text = document?.name?.substringAfterLast(".")
            documentsItemPageCount.text = document?.pageCount.toString() + " Sayfa"
            //documentsItemUploadDate.text = document?.uploadDate?.substring(0,10)

            documentItemNextButton.setOnClickListener {
                document?.let { mDocument -> onItemClickListener?.invoke(mDocument) }
            }

            root.setOnClickListener {
                document?.let { mDocument -> onItemSelectListener?.invoke(mDocument) }
            }
        }
    }

}
typealias Result = DocumentsResponse.Documents.Result