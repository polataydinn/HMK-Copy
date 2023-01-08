package com.application.hmkcopy.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.application.hmkcopy.R
import com.application.hmkcopy.databinding.ActivityMainBinding
import com.application.hmkcopy.presentation.home.copy_center.PriceRowView
import com.application.hmkcopy.service.response.SellersResponseItem
import com.application.hmkcopy.util.AppPermission
import com.application.hmkcopy.util.AppPermission.Companion.permissionGranted
import com.application.hmkcopy.util.AppPermission.Companion.requestPermission
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var pdfUri: Uri

    private var messageBoxInstance: AlertDialog? = null
    /**
     * Activity navController
     */
    val navController get() = navHostFragment.navController

    val bottomSheetContainer get() = binding.bottomSheetContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainerView.id) as NavHostFragment
        instance = this
        setUpBottomNavigationListener()
        setUpBottomSheet()
        binding.mainFabButton.tag = R.drawable.ic_plus
    }

    private fun setUpBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 600
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            this.addBottomSheetCallback(object  : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                        bottomSheetContainer.isVisible = false
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })
        }
    }

    fun shouldRefreshDocuments(refresh: (Boolean) -> Unit){
        viewModel.isDialogDisable.observe(this){
            if (it && messageBoxInstance != null){
                messageBoxInstance?.dismiss()
                refresh.invoke(true)
                viewModel.isDialogDisable.value = false
            }
        }
    }

    fun setPageTitle(title: String) {
        binding.mainPageTitle.text = title
        binding.mainPageTitle.visibility = View.VISIBLE
        val layoutParams: ConstraintLayout.LayoutParams =
            binding.mainPageTitle.layoutParams as ConstraintLayout.LayoutParams
        //layoutParams.horizontalBias = 0.4f
        //layoutParams.bottomToBottom = binding.root.id
    }

    fun setPageTitleInvisible(){
        binding.mainPageTitle.visibility = View.INVISIBLE
    }

    fun setPageDescInvisible(){
        binding.mainPageDesc.visibility = View.INVISIBLE
    }

    fun setPageDesc(description: String) {
        binding.mainPageDesc.text = description
        binding.mainPageDesc.visibility = View.VISIBLE
        val layoutParams: ConstraintLayout.LayoutParams =
            binding.mainPageTitle.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.horizontalBias = 0.2f
    }

    fun setAvatarVisible() {
        binding.mainUserAvatarCardView.visibility = View.VISIBLE
        binding.mainBackButton.visibility = View.GONE
    }

    fun setBackButtonVisible() {
        binding.mainBackButton.visibility = View.VISIBLE
        binding.mainUserAvatarCardView.visibility = View.GONE
    }

    fun changeMainIconToArrow() {
        binding.mainFabButtonIcon.setImageResource(R.drawable.ic_arrow_right_main)
        binding.mainFabButton.tag = R.drawable.ic_arrow_right_main
    }

    fun setFabButtonClickListener(onItemClickListener: () -> Unit) {
        binding.mainFabButton.setOnClickListener {
            onItemClickListener()
            if (binding.mainFabButton.tag == R.drawable.ic_plus){
                createDocumentUploadDialog()
            }
        }
    }

    private fun createDocumentUploadDialog() {
        val messageBoxView = LayoutInflater.from(this)
            .inflate(R.layout.fragment_document_upload, null)
        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)
        val documentUploadButton = messageBoxView.findViewById<FrameLayout>(R.id.dialog_document_upload_button)
        messageBoxInstance = messageBoxBuilder.show()

        messageBoxInstance?.window?.attributes?.gravity = Gravity.BOTTOM
        messageBoxInstance?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        documentUploadButton.setOnClickListener {
            if (!permissionGranted(this)) requestPermission(this)
            selectPdf()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppPermission.REQUEST_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission(this)
                Toast.makeText(this, "Dosya izini vermeniz gerekmektedir", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {
                if (data?.data == null){
                    return
                }
                pdfUri = data.data!!
                val uri: Uri = data.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor = applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
                val parcelFileDescriptor =
                    contentResolver.openFileDescriptor(uri, "r", null) ?: return
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(cacheDir, pdfName ?: "testName")
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                viewModel.uploadDocument(file = file, name = pdfName ?: "testName")
            }
        }
    }

    fun setBackButtonListeners(onItemClickListener: () -> Unit){
        binding.mainBackButton.setOnClickListener {
            onItemClickListener()
        }
    }

    fun changeMainIconToPlus() {
        binding.mainFabButtonIcon.setImageResource(R.drawable.ic_plus)
        binding.mainFabButton.tag = R.drawable.ic_plus
    }

    private fun setUpBottomNavigationListener() {
        binding.apply {
            mainDocumentsButton.setOnClickListener {

            }

            mainOrdersButton.setOnClickListener {

            }
        }
    }

    fun setUpBottomSheet(seller: SellersResponseItem) {
        binding.content.apply {
            storeName.text = seller.name
            storeAddress.text = seller.address
            storePhone.text = seller.phone
            priceList.removeAllViews()
            seller.prices?.paper?.filterNotNull()?.forEach {
                priceList.addView(PriceRowView(this@MainActivity, it))
            }
        }
    }

    companion object {
        var instance: MainActivity? = null
    }
}