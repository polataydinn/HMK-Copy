package com.application.hmkcopy.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseLinearLayout
import com.application.hmkcopy.databinding.UtilLoadingViewBinding
import com.application.hmkcopy.util.extentions.backgroundColor

class LoadingView(
    context: Context,
    attrs: AttributeSet? = null,
) : BaseLinearLayout<UtilLoadingViewBinding>(context, attrs) {

    override fun createView(
        inflater: LayoutInflater,
    ) = UtilLoadingViewBinding.inflate(inflater, this, true)

    override fun viewCreated(attrs: AttributeSet?) {
    }

    fun set(isOpaque: Boolean = false, isWhite: Boolean = false, alpha: Float? = null) {
        binding.subView.apply {
            backgroundColor(if (isWhite) R.color.app_white else R.color.app_black_1)
            if (alpha != null) {
                this.alpha = alpha
            } else {
                this.alpha = if (isOpaque) 1F else 0.1F
            }
        }
    }

}