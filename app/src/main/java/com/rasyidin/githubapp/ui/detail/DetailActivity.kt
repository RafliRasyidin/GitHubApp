package com.rasyidin.githubapp.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.adapter.SectionPagerAdapter
import com.rasyidin.githubapp.core.adapter.SectionPagerAdapter.Companion.TAB_TITLES
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.utils.loadImage
import com.rasyidin.githubapp.core.utils.toShortNumberDisplay
import com.rasyidin.githubapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    private var mediator: TabLayoutMediator? = null

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.title = null

        val username = intent.getStringExtra(EXTRA_DATA)

        subscribeToObserver(username)

        onFavoriteClicked()

        initViewPager()

        initTabLayout()

        onBackClicked()

    }

    private fun onFavoriteClicked() {
        binding.toolbar.imgFavorite.setOnClickListener {
            user?.let {
                val state = !it.isFavorite
                if (state) {
                    viewModel.insertFavorite(it)
                    Snackbar.make(binding.root, R.string.favorited, Snackbar.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteFavorite(it)
                    Snackbar.make(binding.root, R.string.unfavorited, Snackbar.LENGTH_SHORT).show()
                }
                user?.isFavorite = state
                favoriteState(state, binding.toolbar.imgFavorite)
            }
        }
    }

    private fun subscribeToObserver(username: String?) {
        viewModel.getDetailUser(username)
        viewModel.detailUser.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    if (data != null) {
                        if (user == null) {
                            user = data
                            showDetail(data)
                        } else {
                            showDetail(user)
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

    private fun showDetail(user: User?) {
        binding.apply {
            user?.let { user ->
                detailContainer.imgUser.loadImage(
                    user.avatar,
                    R.drawable.ic_github,
                    R.drawable.ic_broken_image
                )
                detailContainer.desc.tvRepository.text = user.repository.toShortNumberDisplay()
                detailContainer.desc.tvFollowing.text = user.following.toShortNumberDisplay()
                detailContainer.desc.tvFollowers.text = user.follower.toShortNumberDisplay()
                toolbar.tvUsername.text = user.username
                detailContainer.tvName.text = user.name
                detailContainer.tvCompany.text = user.company
                detailContainer.tvLocation.text = user.location

                toolbar.imgFavorite.apply {
                    favoriteState(user.isFavorite, this)
                }
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

    private fun onBackClicked() {
        binding.toolbar.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediator?.detach()
        mediator = null
        binding.vp.viewPager.adapter = null
    }

    private fun favoriteState(state: Boolean, image: ImageView) {
        image.apply {
            if (state) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_favorite_green
                    )
                )
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_favorite_border_green
                    )
                )
            }
        }
    }

}