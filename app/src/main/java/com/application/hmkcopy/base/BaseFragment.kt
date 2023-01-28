package com.application.hmkcopy.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.navigation.NavigationCommand
import com.application.hmkcopy.presentation.authentication.AuthenticationActivity
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.profile.ProfileActivity
import com.application.hmkcopy.presentation.splash.SplashActivity
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.util.LoadingView
import com.application.hmkcopy.util.extentions.delayp
import com.yagmurerdogan.toasticlib.Toastic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel?> : Fragment() {

    lateinit var binding: V
    lateinit var navController: NavController
    abstract val viewModel: VM?

    abstract fun layoutResource(inflater: LayoutInflater, container: ViewGroup?): V
    open fun configureObservers() {}
    open fun configureCallbacks() {}
    open fun updateUI() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = layoutResource(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        observeNavigation()
        updateUI()
        configureObservers()
        configureCallbacks()
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel?.navigation?.collectLatest {
                handleNavigation(it)
                println("Destination Observe navigate")
            }
        }
        viewModel?.errorMessage?.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.message?.let { message ->
                errorToast(message)
                Log.d("TAG", "observeNavigation: $message")
            }
        }
        viewModel?.successMessage?.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                successToast(message)
                Log.d("TAG", "observeNavigation: $message")
            }
        }
        viewModel?.progress?.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { progress ->
                if (progress) {
                    showProgress()
                } else {
                    hideProgress()
                }
            }
        }

    }

    private fun errorToast(text: String) {
        context?.let {
            Toastic.toastic(
                context = it,
                message = text,
                duration = Toastic.LENGTH_SHORT,
                type = Toastic.ERROR,
                isIconAnimated = true
            ).show()
        }

    }
    private fun successToast(text: String) {
        context?.let {
            Toastic.toastic(
                context = it,
                message = text,
                duration = Toastic.LENGTH_SHORT,
                type = Toastic.SUCCESS,
                isIconAnimated = true
            ).show()
        }

    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> navController.navigate(navCommand.directions)
            is NavigationCommand.ToId -> navController.navigate(
                navCommand.directionId,
                navCommand.bundle
            )
            is NavigationCommand.Back -> navController.popBackStack()
            is NavigationCommand.ToUri -> navController.navigate(navCommand.uri)
            is NavigationCommand.ToActivity<*> -> {
                val intent = Intent(activity, navCommand.activity)
                startActivity(intent)
                activity?.finishAffinity()
            }
        }
    }

    private fun showProgress() {
        val parent = binding.root as? ViewGroup
        val child = parent?.children?.firstOrNull { it.tag == -946 }
        if (child == null) {
            val progress = context?.let { LoadingView(it) }
            progress?.set()
            delayp(100) {
                progress?.let {
                    progress.tag = -946
                    parent?.addView(it)
                }
            }
        }
    }

    private fun hideProgress() {
        val parent = binding.root as? ViewGroup
        val child = parent?.children?.firstOrNull { it.tag == -946 }
        child?.let {
            parent.removeView(it)
        }
    }

    override fun onPause() {
        super.onPause()
        /*val s = object : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String? {
                Glide.get(applicationContext).clearDiskCache()
                return null
            }
        }
        s.execute()*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private inline fun <reified T> activityAs(): T? {
        return activity as? T
    }

    fun mainActivity(): MainActivity? = activityAs<MainActivity>()

    fun authenticationActivity(): AuthenticationActivity? = activityAs<AuthenticationActivity>()

    fun splashActivity(): SplashActivity? = activityAs<SplashActivity>()

    fun profileActivity(): ProfileActivity? = activityAs<ProfileActivity>()

}