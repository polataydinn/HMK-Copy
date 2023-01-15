package com.application.hmkcopy.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding

abstract class BaseLinearLayout<VB : ViewBinding>(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    lateinit var binding: VB


    open fun createView(inflater: LayoutInflater): VB? {
        return null
    }

    abstract fun viewCreated(attrs: AttributeSet?)

    init {
        this.createView(LayoutInflater.from(context))?.let {
            binding = it
        }
        this.viewCreated(attrs)
    }

    /*var loadingModel: LoadingView.LoadingModel? = null

    private fun showProgress() {
        if (loadingModel == null) {
            loadingModel = LoadingView.show(binding.root as? ViewGroup)
        }
    }

    private fun hideProgress() {
        loadingModel?.let {
            it.parent.removeView(it.self)
        }
        loadingModel = null
    }*/

    /*fun progress(boolean: Boolean) {
        if (boolean) showProgress() else hideProgress()
    }*/

}