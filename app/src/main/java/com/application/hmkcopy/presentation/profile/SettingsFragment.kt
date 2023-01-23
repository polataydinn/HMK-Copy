package com.application.hmkcopy.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentSettingsBinding
import com.application.hmkcopy.presentation.authentication.AuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListeners()
    }

    override fun updateUI() {
        super.updateUI()
        profileActivity()?.setTitleVisibility(true)
        profileActivity()?.setTitleText("Ayarlar")
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_settings"){
                viewModel.navigateBack()
            }
        }
    }

    private fun setButtonListeners() {
        binding.apply {
            settingsMailButton.setOnClickListener {
                viewModel.navigate(SettingsFragmentDirections.actionSettingsFragmentToMailFragment())
            }
            settingsUserProfileButton.setOnClickListener {
                viewModel.navigate(SettingsFragmentDirections.actionSettingsFragmentToUserInfoFragment())
            }
            settingsPasswordButton.setOnClickListener {
                viewModel.navigate(SettingsFragmentDirections.actionSettingsFragmentToPasswordFragment())
            }
            settingsNotificationButton.setOnClickListener {
                viewModel.navigate(SettingsFragmentDirections.actionSettingsFragmentToNotificationsFragment())
            }
            settingsAboutUnicopyButton.setOnClickListener {
                viewModel.navigate(SettingsFragmentDirections.actionSettingsFragmentToAboutFragment())
            }
            settingsUserAgreementButton.setOnClickListener {
                viewModel.navigate(SettingsFragmentDirections.actionSettingsFragmentToUserAgreementFragment())
            }
            settingsExitButton.setOnClickListener {
                viewModel.logout()
                val intent = Intent(profileActivity(), AuthenticationActivity::class.java)
                startActivity(intent)
                profileActivity()?.finish()
            }
        }
    }
}