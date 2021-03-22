package com.rasyidin.githubapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getParcelableExtra(EXTRA_DATA)

        showDetailUser(user)
    }

    private fun showDetailUser(user: User?) {
        binding.apply {
            user?.let {
                supportActionBar?.title = it.username
                Glide.with(this@DetailActivity)
                    .load(resources.getIdentifier(it.avatar, "Drawable", packageName))
                    .into(imgUser)

                desc.tvRepository.text = it.repository.toString()
                desc.tvFollowing.text = it.following.toString()
                desc.tvFollowers.text = it.follower.toString()

                tvName.text = it.name
                tvCompany.text = it.company
                tvLocation.text = it.location

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}