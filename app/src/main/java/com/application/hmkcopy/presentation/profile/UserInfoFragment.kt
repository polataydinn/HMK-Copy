package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentUserInfoBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserInfoBinding {
        return FragmentUserInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
    }

    override fun configureObservers() {
        super.configureObservers()
        viewModel.userData.observe(viewLifecycleOwner){
            Glide.with(binding.profileUserSettingsAvatarImage)
                .load(it.user?.avatar?.url)
                .centerCrop()
                .placeholder(R.color.black)
                .into(binding.profileUserSettingsAvatarImage)

            binding.profileMailEdittext.setText(it.user?.email)
            binding.profileFragmentPhoneNumberEdittext.setText(it.user?.phone)
            binding.profileUsernameEdittext.setText(it.user?.name)
            binding.profileFragmentPhoneNumberEdittext.isEnabled = false
        }
    }

    override fun updateUI() {
        super.updateUI()
        profileActivity()?.setTitleVisibility(true)
        profileActivity()?.setTitleText("Kullanıcı Bilgileri")
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_user_info"){
                viewModel.navigateBack()
            }
        }

        binding.userInfoUpdateButton.setOnClickListener {
            val name = binding.profileUsernameEdittext.text.toString()
            val email = binding.profileMailEdittext.text.toString()
            viewModel.updateMe(email, email, name)
        }
    }
}