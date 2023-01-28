package com.application.hmkcopy.presentation.home

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.application.hmkcopy.R
import com.application.hmkcopy.databinding.ActivityMainBinding
import com.application.hmkcopy.presentation.authentication.AuthenticationActivity
import com.application.hmkcopy.presentation.home.copy_center.PriceRowView
import com.application.hmkcopy.presentation.profile.ProfileActivity
import com.application.hmkcopy.presentation.scanner.ImageCropDialog
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.service.response.SellersResponseItem
import com.application.hmkcopy.util.AppPermission
import com.application.hmkcopy.util.AppPermission.Companion.permissionGranted
import com.application.hmkcopy.util.AppPermission.Companion.requestPermission
import com.application.hmkcopy.util.HMCopyFileProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var documentImageUri: Uri
    private lateinit var cameraPhotoDocFile: File
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

    var currentPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getUserData()
        viewModel.userData.observe(this){
            Glide.with(binding.mainUserAvatarImage)
                .load(it.user?.avatar?.url)
                .centerCrop()
                .placeholder(R.color.black)
                .into(binding.mainUserAvatarImage)

            UserHelper.userEmail = it.user?.email ?: ""
            UserHelper.userName = it.user?.name ?: ""
        }

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainerView.id) as NavHostFragment
        instance = this
        setUpBottomSheet()
        binding.mainFabButton.tag = R.drawable.ic_plus
        val uri = intent.data
        uri?.let {
            currentPath = it.toString()
        }
        binding.mainUserAvatarCardView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        if (!permissionGranted(this)) requestPermission(this)
    }


    override fun onStart() {
        super.onStart()
        if (UserHelper.tokens == null) {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun setDocumentButtonListener(onItemClickListener: () -> Unit) {
        binding.mainDocumentsButton.setOnClickListener {
            binding.mainOrdersButton.alpha = 0.4f
            binding.mainDocumentsButton.alpha = 1.0f
            onItemClickListener.invoke()
        }
    }

    fun setOrderDetailButtonListener(onItemClickListener: () -> Unit) {
        binding.mainOrdersButton.setOnClickListener {
            binding.mainOrdersButton.alpha = 1.0f
            binding.mainDocumentsButton.alpha = 0.4f
            onItemClickListener.invoke()
        }
    }

    private fun setUpBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 600
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            this.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        bottomSheetContainer.isVisible = false
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })
        }
    }

    fun shouldRefreshDocuments(refresh: (Boolean) -> Unit) {
        viewModel.isDialogDisable.observe(this) {
            if (it && messageBoxInstance != null) {
                messageBoxInstance?.dismiss()
                refresh.invoke(true)
                viewModel.isDialogDisable.value = false
            }
        }
    }

    fun setPageTitle(title: String) {
        binding.mainPageTitle.text = title
        binding.mainPageTitle.visibility = View.VISIBLE
    }

    fun setPageTitleInvisible() {
        binding.mainPageTitle.visibility = View.INVISIBLE
    }

    fun setPageDescInvisible() {
        binding.mainPageDesc.visibility = View.INVISIBLE
          val layoutParams: ConstraintLayout.LayoutParams =
        binding.mainPageTitle.layoutParams as ConstraintLayout.LayoutParams
          layoutParams.horizontalBias = 0.35f
          layoutParams.verticalBias = 0.5f
         binding.mainPageTitle.layoutParams = layoutParams
    }

    fun setPageDesc(description: String) {
        binding.mainPageDesc.text = description
        binding.mainPageDesc.visibility = View.VISIBLE
        val layoutParamsTitle: ConstraintLayout.LayoutParams = binding.mainPageTitle.layoutParams as ConstraintLayout.LayoutParams
        layoutParamsTitle.horizontalBias = 0.25f
        layoutParamsTitle.verticalBias = 0.0f
        binding.mainPageTitle.layoutParams = layoutParamsTitle
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
        binding.mainFabButtonIcon.visibility = View.VISIBLE
        binding.mainFabChooseButton.visibility = View.GONE
        binding.mainFabButton.tag = R.drawable.ic_arrow_right_main
    }

    fun changeIconToRefresh() {
        binding.mainFabButtonIcon.setImageResource(R.drawable.ic_refresh_white)
        binding.mainFabButtonIcon.visibility = View.VISIBLE
        binding.mainFabChooseButton.visibility = View.GONE
        binding.mainFabButton.tag = R.drawable.ic_refresh_white
    }
    fun changeMainIconToClose() {
        binding.mainFabButtonIcon.setImageResource(R.drawable.ic_close_white)
        binding.mainFabButtonIcon.visibility = View.VISIBLE
        binding.mainFabChooseButton.visibility = View.GONE
        binding.mainFabButton.tag = R.drawable.ic_arrow_right_main
    }

    fun setFabButtonClickListener(onItemClickListener: () -> Unit) {
        binding.mainFabButton.setOnClickListener {
            onItemClickListener()
            if (binding.mainFabButton.tag == R.drawable.ic_plus) {
                createDocumentUploadDialog()
            }
        }
    }


    private fun createDocumentUploadDialog() {
        val messageBoxView = LayoutInflater.from(this)
            .inflate(R.layout.fragment_document_upload, null)
        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)
        val documentUploadButton =
            messageBoxView.findViewById<FrameLayout>(R.id.dialog_document_upload_button)
        messageBoxInstance = messageBoxBuilder.show()

        messageBoxInstance?.window?.attributes?.gravity = Gravity.BOTTOM
        messageBoxInstance?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        documentUploadButton.setOnClickListener {
            if (!permissionGranted(this)) requestPermission(this)
            dispatchTakePictureDocumentIntent()
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

    private fun scanner(documentImageUri: Uri) {
        val dialog = ImageCropDialog(imageUri = documentImageUri) {

        }
        dialog.show(supportFragmentManager, "hello world")
    }

    private fun dispatchTakePictureDocumentIntent() {
        cameraPhotoDocFile = createImageFile()
        documentImageUri = FileProvider.getUriForFile(
            this,
            HMCopyFileProvider.AUTHORITY,
            cameraPhotoDocFile
        )
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, documentImageUri)
        }
        startActivityForResult(takePictureIntent, 1)
    }

    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("ddMMyyyy", Locale.ROOT).format(Date())
        val imageFileName = "photo_${timestamp}_"
        val storageDir = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DCIM), HMCopyFileProvider.PATH_PICTURES)
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir)
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
            1 -> {
                scanner(documentImageUri)
            }
            12 -> if (resultCode == RESULT_OK) {
                if (data?.data == null) {
                    return
                }
                pdfUri = data.data!!
                val uri: Uri = data.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor =
                            applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
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

    fun setBackButtonListeners(onItemClickListener: () -> Unit) {
        binding.mainBackButton.setOnClickListener {
            onItemClickListener()
        }
    }

    fun changeMainIconToPlus() {
        binding.mainFabButtonIcon.setImageResource(R.drawable.ic_plus)
        binding.mainFabButton.tag = R.drawable.ic_plus
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

    fun makeBottomButtonsInvisible() {
        binding.mainDocumentsButton.visibility = View.GONE
        binding.mainOrdersButton.visibility = View.GONE
    }

    fun makeBottomButtonsVisible() {
        binding.mainDocumentsButton.visibility = View.VISIBLE
        binding.mainOrdersButton.visibility = View.VISIBLE
    }

    fun makeFabButtonToChoose() {
        binding.mainFabButtonIcon.visibility = View.GONE
        binding.mainFabChooseButton.visibility = View.VISIBLE
        binding.mainFabButton.tag = 1000
    }

    fun makeFabButtonToLeftArrow() {
        binding.mainFabButtonIcon.visibility = View.VISIBLE
        binding.mainFabChooseButton.visibility = View.GONE
    }

    fun makeBottomNavigationVisible() {
        binding.mainBottomNavigation.visibility = View.VISIBLE
    }

    fun makeBottomNavigationInvisible() {
        binding.mainBottomNavigation.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.label == "OrderDetailFragment") {
            binding.mainDocumentsButton.callOnClick()
        } else if (navController.currentDestination?.label == "fragment_payment_web_view"){
            viewModel.popBackToMain()
        } else{
            super.onBackPressed()
        }
    }

    @SuppressLint("Range")
    fun startFile(fileUrl: String?) {
        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        val uri: Uri =
            Uri.parse(fileUrl)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        val documentNamePrev = fileUrl?.substringAfterLast(".s3.eu-central-1.amazonaws.com/")
        val documentExt = documentNamePrev?.substringAfter(".")?.substringBefore("-")
        val documentName = documentNamePrev?.substringBefore(".")
        val finalDocumentName = "$documentName.$documentExt"
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            finalDocumentName
        )
        val reference = manager?.enqueue(request)
        val query = reference?.let { DownloadManager.Query().setFilterById(it) }
        val cursor = manager?.query(query)

        if (cursor?.moveToFirst() == true) {
            val fileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
            val intent = Intent(Intent.ACTION_VIEW)
            try {
                intent.setDataAndType(Uri.parse(fileUri), "application/$documentExt")
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            } catch (e: java.lang.Exception) {
            }
        }
        cursor?.close()
    }

    fun downloadFile(url: String?) {
        val documentNamePrev = url?.substringAfterLast(".s3.eu-central-1.amazonaws.com/")
        val documentExt = documentNamePrev?.substringAfter(".")?.substringBefore("-")
        val documentName = documentNamePrev?.substringBefore(".")
        val finalDocumentName = "$documentName.$documentExt"
        var localUri: Uri? = null

        val request: DownloadManager.Request
        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url) // A url to download a file
        try {
            request = DownloadManager.Request(uri)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            return
        }
        request.setVisibleInDownloadsUi(true)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        try {
            val downloadFileDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getAbsolutePath() + "/Contents"
            )
            if (downloadFileDir != null) {
                if (!downloadFileDir.exists()) {
                    downloadFileDir.mkdirs()
                }
                val file = File(downloadFileDir.absolutePath + File.separator + finalDocumentName)
                if (file.exists()) {
                    file.delete()
                }
                localUri = Uri.fromFile(file)
                request.setDestinationUri(localUri)
                if (localUri != null) {
                    request.setMimeType(
                        MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(localUri.toString()))
                    )
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        request.setTitle(finalDocumentName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        try {
            manager.enqueue(request)
        } catch (e: SecurityException) {
            e.printStackTrace()
            //Got exception here
        }
    }

    companion object {
        var instance: MainActivity? = null
    }
}