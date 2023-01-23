package com.application.hmkcopy.presentation.home.copy_center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowCopyCenterItemBinding
import com.application.hmkcopy.service.response.SellersResponseItem
import com.application.hmkcopy.util.extentions.SizeUtils

class CopyCenterAdapter :
    BaseListAdapter<SellersResponseItem, CopyCenterViewHolder>(DIFF_UTIL_CALLBACK) {

    var onShowInMapClick: ((SellersResponseItem) -> Unit)? = null
    var onItemClick: ((SellersResponseItem) -> Unit)? = null

    override fun bindView(holder: CopyCenterViewHolder, position: Int, item: SellersResponseItem) {
        holder.bind(getItem(position), onShowInMapClick, onItemClick, itemCount, position)
    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): CopyCenterViewHolder {
        return CopyCenterViewHolder(
            RowCopyCenterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<SellersResponseItem>() {
            override fun areItemsTheSame(
                oldItem: SellersResponseItem,
                newItem: SellersResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SellersResponseItem,
                newItem: SellersResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class CopyCenterViewHolder(private val binding: RowCopyCenterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: SellersResponseItem?,
        onShowInMapClick: ((SellersResponseItem) -> Unit)?,
        onItemClick: ((SellersResponseItem) -> Unit)?,
        itemCount: Int,
        position: Int
    ) {

        binding.apply {
            if (itemCount == position + 1) {
                val rootParams = root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(root.context, 56)
            } else {
                val rootParams = root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(root.context, 0)
            }
            val address = item?.address.toString() + "\n" + item?.phone
            copyCenterAddress.text = address
            copyCenterName.text = item?.name
            root.setOnClickListener {
                item?.let { mItem -> onItemClick?.invoke(mItem) }
            }
            copyCenterShowInMap.setOnClickListener {
                item?.let { mItem -> onShowInMapClick?.invoke(mItem) }
            }
        }
    }

}
