package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentMailBinding
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

    }

    override fun updateUI() {
        super.updateUI()
        profileActivity()?.setTitleVisibility(true)
        profileActivity()?.setTitleText("Eposta Ayarları")
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_mail"){
                viewModel.navigateBack()
            }
        }
    }
}