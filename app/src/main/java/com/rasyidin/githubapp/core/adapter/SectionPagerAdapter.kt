package com.rasyidin.githubapp.core.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.ui.detail.FollowersFollowingFragment

class SectionPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return FollowersFollowingFragment.newInstance(position)
    }
}