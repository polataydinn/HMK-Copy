package com.application.hmkcopy.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.application.hmkcopy.navigation.NavigationCommand
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.onboarding.OnboardingActivity
import com.application.hmkcopy.presentation.splash.SplashActivity
import kotlinx.coroutines.flow.collectLatest

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
//            Toast.makeText(context?.applicationContext, it.getContentIfNotHandled()?.message, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "observeNavigation: ${it.getContentIfNotHandled()?.message}")
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> navController.navigate(navCommand.directions)
            is NavigationCommand.ToId -> navController.navigate(navCommand.directionId, navCommand.bundle)
            is NavigationCommand.Back -> navController.popBackStack()
            is NavigationCommand.ToUri -> navController.navigate(navCommand.uri)
            is NavigationCommand.ToActivity<*> -> {
                val intent = Intent(activity, navCommand.activity)
                startActivity(intent)
                activity?.finishAffinity()
            }
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

    fun splashActivity(): SplashActivity? = activityAs<SplashActivity>()

}