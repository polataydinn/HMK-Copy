package com.application.hmkcopy.presentation.home.copy_center

import android.os.Parcelable
import com.application.hmkcopy.service.response.BasketOptionsResponse
import com.application.hmkcopy.service.response.CheckoutResponse
import com.application.hmkcopy.service.response.Prices
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
    id = id ?: "",
    name = name ?: "",
    price = price ?: 0.0
)

fun CheckoutResponse.Checkout.Product.Prices.toDomain() = ProductListItem.Prices(
    coloredPrice = coloredPrice ?: 0.0,
    price = price ?: 0.0,
    spiralledPrice = spiralledPrice ?: 0.0
)

fun CheckoutResponse.toDomain(): List<ProductListItem> {
    return checkout.products.map {
        ProductListItem(
            name = it?.document?.name ?: "",
            id = it?.id ?: "",
            prices = it?.prices?.toDomain() ?: ProductListItem.Prices(),
            priceInfoText = it?.priceInfoText?.map { it?.toDomain()?: listOf<ProductListItem.PriceInfoText>() } as List<ProductListItem.PriceInfoText>,
            uploadDate = it.document?.uploadDate ?: "",
            printOptions = it.printOptions ?: CheckoutResponse.Checkout.Product.PrintOptions()
        )
    }
}