package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentUserInfoBinding
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

    override fun updateUI() {
        super.updateUI()
        profileActivity()?.setTitleVisibility(true)
        profileActivity()?.setTitleText("Kullanıcı Bilgileri")
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_user_info"){
                viewModel.navigateBack()
            }
        }
    }
}