package com.application.hmkcopy.presentation.home.copy_center

import android.os.Parcelable
import com.application.hmkcopy.service.response.BasketOptionsResponse
import com.application.hmkcopy.service.response.CheckoutResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductListItem(
    val name: String,
    val id: String,
    val prices: Prices,
    val priceInfoText: List<PriceInfoText>,
    val uploadDate: String,
    val printOptions: CheckoutResponse.Checkout.Product.PrintOptions,
    val basketOptions: List<BasketOptionsResponse.PrintOption> = listOf(),
    val isExpanded: Boolean = false
) : Parcelable {
    @Parcelize
    data class Prices(
        val coloredPrice: Double = 0.0,
        val price: Double = 0.0,
        val spiralledPrice: Double = 0.0
    ) : Parcelable
    @Parcelize
    data class PriceInfoText(
        val id: String = "",
        val name: String = "",
        val price: Double = 0.0
    ) : Parcelable
}

fun CheckoutResponse.Checkout.Product.PriceInfoText.toDomain() = ProductListItem.PriceInfoText(
    id = id,
    name = name,
    price = price
)

fun CheckoutResponse.Checkout.Product.Prices.toDomain() = ProductListItem.Prices(
    coloredPrice = coloredPrice,
    price = price,
    spiralledPrice = spiralledPrice
)

fun CheckoutResponse.toDomain(): List<ProductListItem> {
    return checkout.products.map {
        ProductListItem(
            name = it.document.name,
            id = it.id,
            prices = it.prices.toDomain(),
            priceInfoText = it.priceInfoText.map { it.toDomain() },
            uploadDate = "13-01-2023",
            printOptions = it.printOptions
        )
    }
}