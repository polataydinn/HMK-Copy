package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentMailBinding
import com.application.hmkcopy.repository.user.UserHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MailFragment : BaseFragment<FragmentMailBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMailBinding {
        return FragmentMailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (UserHelper.userEmail.isEmpty()) {
            binding.currentEmailLayout.visibility = View.GONE
        } else {
            binding.mailCurrentMailEdittext.setText(UserHelper.userEmail)
            binding.mailCurrentMailEdittext.isEnabled = false
        }

        binding.mailUpdateButton.setOnClickListener {
            val email1 = binding.mailNewMailEdittext.text.toString()
            val email2 = binding.mailRepeatNewMailEdittext.text.toString()
            viewModel.updateMe(email1, email2)
        }
    }

    override fun updateUI() {
        super.updateUI()
        profileActivity()?.setTitleVisibility(true)
        profileActivity()?.setTitleText("Eposta Ayarları")
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_mail") {
                viewModel.navigateBack()
            }
        }
    }
}