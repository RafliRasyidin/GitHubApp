package com.rasyidin.githubapp.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.adapter.SectionPagerAdapter
import com.rasyidin.githubapp.core.adapter.SectionPagerAdapter.Companion.TAB_TITLES
import com.rasyidin.githubapp.core.data.source.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.utils.loadImage
import com.rasyidin.githubapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    private var mediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user: User? = intent.getParcelableExtra(EXTRA_DATA)

        subscribeToObserver(user?.username)

        initViewPager()

        initTabLayout()
    }

    private fun subscribeToObserver(username: String?) {
        viewModel.getDetailUser(username)
        viewModel.detailUser.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { user ->
                        binding.apply {
                            supportActionBar?.title = username
                            imgUser.loadImage(
                                user.avatar,
                                R.drawable.ic_github,
                                R.drawable.ic_broken_image
                            )
                            desc.tvRepository.text = user.repository.toString()
                            desc.tvFollowing.text = user.following.toString()
                            desc.tvFollowers.text = user.follower.toString()
                            tvName.text = user.name
                            tvCompany.text = user.company
                            tvLocation.text = user.location
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, resources.getString(R.string.error), Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun initViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(supportFragmentManager, lifecycle)
        binding.vp.viewPager.apply {
            adapter = sectionPagerAdapter
            offscreenPageLimit = 2
        }
    }

    private fun initTabLayout() {
        mediator = TabLayoutMediator(binding.vp.tabs, binding.vp.viewPager) { tab, pos ->
            tab.text = when (pos) {
                0 -> getString(TAB_TITLES[0])
                else -> getString(TAB_TITLES[1])
            }
        }
        mediator?.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediator?.detach()
        mediator = null
        binding.vp.viewPager.adapter = null
    }

}