package com.application.hmkcopy.presentation.home.copy_center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowPrintSettingsBinding
import com.application.hmkcopy.presentation.home.copy_center.DropDownView
import com.application.hmkcopy.presentation.home.copy_center.ProductListItem
import com.application.hmkcopy.presentation.home.copy_center.SelectableView
import com.application.hmkcopy.util.extentions.invertVisibility

class PrintListAdapter : BaseListAdapter<ProductListItem, PrintListAdapter.ViewHolder>(DiffItem) {

    var onCheckStateChanged: (Boolean, String) -> Unit = { _, _ -> }
    var onItemSelectedListener: (String, Int, String) -> Unit = { _, _, _ -> }
    var onDocumentDownload: (ProductListItem) -> Unit = {}

    inner class ViewHolder(private val binding: RowPrintSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductListItem) {
            binding.apply {
                printSettingsDocumentName.text = item.name
                printSettingsUploadDate.text = item.uploadDate
                printSettingsDocumentPrice.text = "${item.prices.price} TL"
                printSettingsDownloadDocumentButton.setOnClickListener {
                    onDocumentDownload(item)
                }
                printSettingsChooseButton.setOnClickListener {
                    printSettingsLinearContainer.invertVisibility()
                }
                printSettingsLinearContainer.setUpOptions(item)
            }

        }
    }

    private fun LinearLayout.setUpOptions(item: ProductListItem) {
        removeAllViews()
        val papers = item.basketOptions.map { it.size }
        val paperSize = DropDownView(context).apply {
            title = papers[0]
            description = "210 mm x 193 mm"
            items = papers
            onItemSelectedListener = { text, position ->
                this@PrintListAdapter.onItemSelectedListener(text, position, papers[0])
            }
        }
        addView(paperSize)
        val verticalCopy = DropDownView(context).apply {
            title = "Dikey Baskı Yönü"
            description = "Dikey yönlü yazdırma"
            items = listOf("Dikey Baskı Yönü","Yatay Baskı Yönü")
            onItemSelectedListener = { text, position ->
                this@PrintListAdapter.onItemSelectedListener(text, position, items[position])
            }
        }
        addView(verticalCopy)

        item.priceInfoText.map {
            val selectableView = SelectableView(context).apply {
                text1 = it.price.toString()
                text2 = it.name
                isChecked = true
                onCheckStateChanged = { isChecked ->
                    this@PrintListAdapter.onCheckStateChanged(isChecked, it.id)
                }
            }
            addView(selectableView)
        }
    }

    override fun bindView(holder: ViewHolder, position: Int, item: ProductListItem) {
        holder.bind(item)
    }


    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): ViewHolder {
        val binding = RowPrintSettingsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    object DiffItem : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductListItem,
            newItem: ProductListItem
        ): Boolean {
            return oldItem == newItem
        }

    }
}
