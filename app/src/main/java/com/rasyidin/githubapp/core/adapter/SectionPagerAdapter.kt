package com.rasyidin.githubapp.core.adapter

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.ui.detail.FollowersFollowingFragment
import com.rasyidin.githubapp.ui.detail.RepositoryFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String?) :
    FragmentStateAdapter(activity) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.following, R.string.followers, R.string.repository)
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0, 1 -> FollowersFollowingFragment.newInstance(position, username)
            else -> RepositoryFragment.newInstance(username)
        }
    }
}