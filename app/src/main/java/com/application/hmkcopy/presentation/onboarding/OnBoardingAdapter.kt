package com.application.hmkcopy.presentation.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.hmkcopy.base.BaseAdapter
import com.application.hmkcopy.data.model.OnboardingItem
import com.application.hmkcopy.databinding.OnboardingCardItemBinding

class OnBoardingAdapter: BaseAdapter<OnboardingItem, OnBoardingViewHolder>() {


    override fun bindView(holder: OnBoardingViewHolder, position: Int, item: OnboardingItem) {
        holder.bind(items?.get(position))
    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): OnBoardingViewHolder {
        return OnBoardingViewHolder(OnboardingCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

class OnBoardingViewHolder(private val binding: OnboardingCardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: OnboardingItem?) {
        item?.image?.let { binding.onboardingItemImage.setImageResource(it) }
        binding.onboardingItemTopDescription.text = item?.topDescription
        binding.onboardingItemBottomDescription.text = item?.bottomDescription
    }

}