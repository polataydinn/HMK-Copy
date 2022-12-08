package com.application.hmkcopy.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.data.model.OnboardingItem
import com.application.hmkcopy.databinding.FragmentOnboardingBinding
import com.application.hmkcopy.presentation.authentication.AuthenticationActivity
import com.application.hmkcopy.presentation.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>() {

    override val viewModel: OnboardingViewModel by viewModels()
    private val adapter: OnBoardingAdapter by lazy { OnBoardingAdapter() }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setButtonListeners()
    }

    private fun setButtonListeners() {
        binding.apply {
            onboardingNextButton.setOnClickListener {
                if (binding.onboardingRecyclerview.currentItem == 1) {
                    switchToAuthenticationActivity()
                }
                binding.onboardingRecyclerview.currentItem = 1
            }
            onboardingItemSkipButton.setOnClickListener {
                switchToAuthenticationActivity()
            }
        }
    }

    private fun setRecyclerView() = with(binding) {
        adapter.set(getListOfItems())
        onboardingRecyclerview.adapter = adapter
        dotsIndicator.attachTo(onboardingRecyclerview)
    }

    private fun getListOfItems() = listOf(
        OnboardingItem(
            R.drawable.onboarding_backgoround_item1,
            getString(R.string.first_item_top_desc),
            getString(R.string.first_item_bottom_desc)
        ),
        OnboardingItem(
            R.drawable.onboarding_backgoround_item2,
            getString(R.string.second_item_top_desc),
            getString(R.string.second_item_bottom_desc)
        )
    )

    private fun switchToAuthenticationActivity() {
        activity?.let {
            val intent = Intent(it, MainActivity::class.java)
            startActivity(intent)
            it.finish()
        }
    }


}