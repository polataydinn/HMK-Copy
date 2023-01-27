package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentLoginBinding
import com.application.hmkcopy.repository.user.UserHelper
import com.ccc.ccp.Country
import com.ccc.ccp.CountryCodePicker
import java.util.*

class LoginFragment : BaseFragment<FragmentLoginBinding, AuthenticationViewModel>(), CountryCodePicker.OnRefreshDataCompleteListener {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun updateUI() {
        super.updateUI()
        binding.loginPhoneNumber.setText(UserHelper.phoneNumber)
        setUpCountryCodePicker()
    }

    private fun setUpCountryCodePicker() {
        binding.countryCodePicker.setLanguageCode(Locale.getDefault().language)
        binding.countryCodePicker.refreshDefaultData()
        binding.countryCodePicker.setOnRefreshDataCompleteListener(this)
        binding.countryCodePicker.setOnClickListener {
            onPickerClick()
        }
    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            loginButton.setOnClickListener {
                val phone = binding.loginPhoneNumber.text?.toString() ?: ""
                val password = binding.loginPasswordInput.text?.toString() ?: ""
                viewModel.login(phone, password)
            }
            loginFragmentForgetPasswordButton.setOnClickListener {
                viewModel.navigate(LoginFragmentDirections.toResetPasswordFragment())
            }
        }
    }

    override fun onRefreshDataComplete() {
        binding.countryCodePicker.setCountryPicked(binding.countryCodePicker.getCountry(Locale.getDefault().country))
    }

    fun onCountryPicked(country: Country) {
        binding.countryCodePicker.setCountryPicked(country)
    }

    fun onPickerClick() {
        val country = binding.countryCodePicker.getCountryPicked() ?: return
        val fm = parentFragmentManager
        val newFragment =
            com.ccc.ccp.CountryCodePickerFragment.getInstance(country, Locale.getDefault().language, false)
        newFragment.show(fm, com.ccc.ccp.CountryCodePickerFragment.TAG)
    }

}