package com.application.hmkcopy.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.NavHostFragment
import com.application.hmkcopy.R
import com.application.hmkcopy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    /**
     * Activity navController
     */
    val navController get() = navHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainFragmentContainerView.id) as NavHostFragment
        instance = this
        setUpBottomNavigationListener()
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
    }

    fun setFabButtonClickListener(onItemClickListener: () -> Unit) {
        binding.mainFabButton.setOnClickListener {
            onItemClickListener()
        }
    }

    fun setBackButtonListeners(onItemClickListener: () -> Unit){
        binding.mainBackButton.setOnClickListener {
            onItemClickListener()
        }
    }

    fun changeMainIconToPlus() {
        binding.mainFabButtonIcon.setImageResource(R.drawable.ic_plus)
    }

    private fun setUpBottomNavigationListener() {
        binding.apply {
            mainDocumentsButton.setOnClickListener {

            }

            mainOrdersButton.setOnClickListener {

            }

            mainFabButton.setOnClickListener {

            }
        }
    }

    companion object {
        var instance: MainActivity? = null
    }
}