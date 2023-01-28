package com.application.hmkcopy.presentation.home.my_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.data.model.DocumentTransfer
import com.application.hmkcopy.databinding.FragmentMyOrdersBinding
import com.application.hmkcopy.presentation.home.CommonViewModel
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.service.request.CreateCheckoutBasketRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding, CommonViewModel>() {

    override val viewModel: CommonViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersBinding {
        return FragmentMyOrdersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MyPagerAdapter(childFragmentManager)
        adapter.addFragment(MyOrdersFirstTabFragment(), "Yüklenenler")
        adapter.addFragment(MyOrdersSecondTabFragment(), "Tarananlar")

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                println("")
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList[position]
        }
    }
}
