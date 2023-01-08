package com.application.hmkcopy.base

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.transition.TransitionManager
import androidx.viewbinding.ViewBinding
import com.application.hmkcopy.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<V : ViewBinding> : BottomSheetDialogFragment() {

    lateinit var binding: V
    abstract fun layoutResource(inflater: LayoutInflater, container: ViewGroup?): V
    var isHideable = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = layoutResource(inflater, container)
        return binding.root
    }

    open fun configureObservers() {}
    open fun configureCallbacks() {}
    open fun updateUI() {}



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as? BottomSheetDialog
        return onCreateDialog(dialog) ?: super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    private fun onCreateDialog(dialog: BottomSheetDialog?): BottomSheetDialog? {
        dialog?.dismissWithAnimation = true
        dialog?.setOnShowListener { dialogInterface: DialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val frameLayout = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (frameLayout != null) {
                val bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
                bottomSheetBehavior.skipCollapsed = true
                bottomSheetBehavior.isHideable = isHideable
                bottomSheetBehavior.isFitToContents = true
                bottomSheetBehavior.peekHeight = screenHeight
                bottomSheetBehavior.shouldSkipSmoothAnimation()
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity?.lifecycle?.currentState == Lifecycle.State.CREATED) {
            dismiss()
        }
        updateUI()
        configureObservers()
        configureCallbacks()
    }




}

val displayMetrics: DisplayMetrics? = Resources.getSystem().displayMetrics
val screenWidth = displayMetrics?.widthPixels ?: 0
val screenHeight = displayMetrics?.heightPixels ?: 0