package com.nasa.obvious.ui.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifeCycle) {

    private val fragments = SparseArray<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragments.put(fragments.size(), fragment)
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
