package com.application.hmkcopy.presentation.home.copy_center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.data.model.CopyCenterItem
import com.application.hmkcopy.databinding.RowCopyCenterItemBinding
import com.application.hmkcopy.util.extentions.SizeUtils

class CopyCenterAdapter :
    BaseListAdapter<CopyCenterItem, CopyCenterViewHolder>(DIFF_UTIL_CALLBACK) {

    var onItemClick: (() -> Unit)? = null

    override fun bindView(holder: CopyCenterViewHolder, position: Int, item: CopyCenterItem) {
        holder.bind(getItem(position), onItemClick, itemCount, position)
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
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<CopyCenterItem>() {
            override fun areItemsTheSame(
                oldItem: CopyCenterItem,
                newItem: CopyCenterItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CopyCenterItem,
                newItem: CopyCenterItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class CopyCenterViewHolder(private val binding: RowCopyCenterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CopyCenterItem?, onItemClick: (() -> Unit)?, itemCount: Int, position: Int) {

        binding.apply {
            if (itemCount == position + 1) {
                val rootParams = root.layoutParams as ViewGroup.MarginLayoutParams
                rootParams.bottomMargin = SizeUtils.int2dp(root.context, 56)
            }

            copyCenterAddress.text = item?.centerAddress
            copyCenterName.text = item?.centerName

            copyCenterShowInMap.setOnClickListener {
                onItemClick?.invoke()
            }
        }
    }

}
