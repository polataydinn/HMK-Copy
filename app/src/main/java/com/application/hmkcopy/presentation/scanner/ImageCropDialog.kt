package com.application.hmkcopy.presentation.scanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.application.hmkcopy.databinding.DialogImageCropBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageCropDialog(
    private val imageUri: Uri,
    private val onImageCropped: (Bitmap) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogImageCropBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogImageCropBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cropButton.isEnabled = false
            cropImage.setOnLoadListener { loading ->
                cropProgressBar.isVisible = loading
                cropButton.isEnabled = !loading
            }
            cropImage.setImage(requireContext().contentResolver.openInputStream(imageUri).run {
                BitmapFactory.decodeStream(this)
            })
            cropButton.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    withContext(Dispatchers.Main) {
                        cropProgressBar.isVisible = true
                        cropButton.isEnabled = false
                    }
                    val image = cropImage.getCroppedImage()
                    withContext(Dispatchers.Main) {
                        onImageCropped(image)
                        cropProgressBar.isVisible = false
                        cropButton.isEnabled = true
                        dismiss()
                    }
                }
            }
        }
    }
}