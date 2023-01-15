package com.application.hmkcopy.presentation.home.copy_center.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseListAdapter
import com.application.hmkcopy.databinding.RowPrintSettingsBinding
import com.application.hmkcopy.presentation.home.copy_center.DropDownView
import com.application.hmkcopy.presentation.home.copy_center.ProductListItem
import com.application.hmkcopy.presentation.home.copy_center.SelectableView
import java.util.*

class PrintListAdapter : BaseListAdapter<ProductListItem, PrintListAdapter.ViewHolder>(DiffItem) {

    var onCheckStateChanged: (Boolean, String, PrintViewOptions) -> Unit = { _, _, _ -> }
    var onItemSelectedListener: (String, String, PrintViewOptions) -> Unit = { _, _, _ -> }
    var onDocumentDownload: (ProductListItem) -> Unit = {}
    var isExpanded: (ProductListItem) -> Unit = {}

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
                printSettingsLinearContainer.isVisible = item.isExpanded
                printSettingsChooseButton.setOnClickListener {
                    isExpanded(item)
                }
                printSettingsLinearContainer.setUpOptions(item)
            }

        }
    }

    private fun LinearLayout.setUpOptions(item: ProductListItem) {
        removeAllViews()
        val papers = item.basketOptions.map { it.size }
        val index = papers.indexOf(item.printOptions.paperSize)

        val paperSize = DropDownView(context).apply {
            title = item.printOptions.paperSize
            description = "210 mm x 193 mm"
            Collections.swap(papers, 0, index)
            items = papers
            onItemSelectedListener = { text, position ->
                this@PrintListAdapter.onItemSelectedListener(
                    text,
                    item.id,
                    PrintViewOptions.PAPER_SIZE
                )
            }
        }
        addView(paperSize)
        val verticalCopy = DropDownView(context).apply {
            title =
                if (item.printOptions.paperDirection == "Dikey") "Dikey Baskı Yönü" else "Yatay Baskı Yönü"
            description =
                if (item.printOptions.paperDirection == "Dikey") "Dikey yönlü yazdırma" else "Yatay yönlü yazdırma"
            items = if (item.printOptions.paperDirection == "Dikey") listOf(
                "Dikey Baskı Yönü",
                "Yatay Baskı Yönü"
            ) else listOf("Yatay Baskı Yönü", "Dikey Baskı Yönü")
            onItemSelectedListener = { text, position ->
                this@PrintListAdapter.onItemSelectedListener(
                    text,
                    item.id,
                    PrintViewOptions.ORIENTATION
                )
            }
        }
        addView(verticalCopy)

        val selectedOptions =
            item.basketOptions.filter { it.size == item.printOptions.paperSize }[0]

        if (selectedOptions.isDoubleSided) {
            val doubleSide = DropDownView(context).apply {
                title =
                    if (item.printOptions.paperSide == 1) "Tek Yönlü Yazdır" else "Arkalı Önlü Yazdır"
                description = "Toplam 50 sayfa olarak basılır"
                items = if (item.printOptions.paperSide == 1) listOf(
                    "Tek Yönlü Yazdır",
                    "Arkalı Önlü Yazdır"
                ) else listOf("Arkalı Önlü Yazdır", "Tek Yönlü Yazdır")
                onItemSelectedListener = { text, position ->
                    this@PrintListAdapter.onItemSelectedListener(
                        text,
                        item.id,
                        PrintViewOptions.SIDE
                    )
                }
            }
            addView(doubleSide)
        } else {
            val oneSide = DropDownView(context).apply {
                title = "Tek Yönlü Yazdır"
                description = "Toplam 50 sayfa olarak basılır"
                arrowVisibility = false
            }
            addView(oneSide)
        }
        val squareItems = prepareSquareItems()
        val indexOfSquare = squareItems.indexOf(paperSquare(item.printOptions.paperSquare))
        val fillThePage = DropDownView(context).apply {
            title = paperSquare(item.printOptions.paperSquare)
            description = paperSquareDescription(item.printOptions.paperSquare)
            Collections.swap(squareItems, 0, indexOfSquare)
            items = squareItems
            onItemSelectedListener = { text, position ->
                this@PrintListAdapter.onItemSelectedListener(
                    text,
                    item.id,
                    PrintViewOptions.SQUARED
                )
            }
        }
        addView(fillThePage)

        if ((item.printOptions.paperSide == 2 && selectedOptions.isDoubleSidedColored)
            || (item.printOptions.paperSide == 1 && selectedOptions.isDoubleSidedColored)
        ) {
            val selectableColoredView = SelectableView(context).apply {
                text1 = "Renkli Baski"
                text2 = "Herbir sayfa için +${item.prices.coloredPrice} TL"
                isChecked = item.printOptions.isColored
                onCheckStateChanged = { isChecked ->
                    this@PrintListAdapter.onCheckStateChanged(
                        isChecked,
                        item.id,
                        PrintViewOptions.COLORED
                    )
                }
            }
            addView(selectableColoredView)
        }
        val selectableSpiralledView = SelectableView(context).apply {
            text1 = "Spiral Ciltleme"
            text2 = "Her bir cilt için +${item.prices.spiralledPrice} TL"
            isChecked = item.printOptions.isSpiralled
            onCheckStateChanged = { isChecked ->
                this@PrintListAdapter.onCheckStateChanged(
                    isChecked,
                    item.id,
                    PrintViewOptions.SPIRALLED
                )
            }
        }
        addView(selectableSpiralledView)

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

    private fun prepareSquareItems(): List<String> {
        return listOf("1x1 Sayfa", "1x2 Sayfa", "1x4 Sayfa")
    }

    private fun paperSquare(paperSquare: Int): String {
        return when (paperSquare) {
            1 -> "1x1 Sayfa"
            2 -> "1x2 Sayfa"
            4 -> "1x4 Sayfa"
            else -> ""
        }
    }

    private fun paperSquareDescription(paperSquare: Int): String {
        return when (paperSquare) {
            1 -> "1 sayfaya sığdır"
            2 -> "İki sayfayı 1 sayfaya sığdır"
            4 -> "Dört sayfayı 1 sayfaya sığdır"
            else -> ""
        }
    }
}

enum class PrintViewOptions {
    PAPER_SIZE,
    ORIENTATION,
    SIDE,
    SQUARED,
    COLORED,
    SPIRALLED,
}
