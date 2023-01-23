package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        settingsButtonListener()
        profileActivity()?.setTitleVisibility(false)
    }

    private fun settingsButtonListener() {
        profileActivity()?.setSettingsButtonListener {
            viewModel.navigate(ProfileFragmentDirections.actionProfileFragmentToSettingsFragment())
        }
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_profile"){
                profileActivity()?.finish()
            }
        }
    }

    override fun configureObservers() {
        super.configureObservers()
        viewModel.userData.observe(viewLifecycleOwner){
            it?.user?.apply {
                binding.profileMailTextview.text = id
                binding.profileFragmentPhoneNumberTextview.text = phone
                binding.profileUsernameTextview.text = name
                binding.textView6.text = name
            }
        }
    }
}