package com.rasyidin.githubapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.adapter.UserAdapter
import com.rasyidin.githubapp.databinding.ActivityFavoriteBinding
import com.rasyidin.githubapp.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var userAdapter: UserAdapter

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite)

        setupRecyclerView()

        subscribeToObserver()

        onItemClick()
    }

    private fun onItemClick() {
        userAdapter.onItemClickListener = { selectedItem ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedItem.username)
            startActivity(intent)
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

    private fun subscribeToObserver() {
        viewModel.getFavorite.observe(this) { users ->
            if (users.isNullOrEmpty()) {
                binding.rvUsers.visibility = View.GONE
                binding.lottieNoData.visibility = View.VISIBLE
            } else {
                binding.rvUsers.visibility = View.VISIBLE
                binding.lottieNoData.visibility = View.GONE
                userAdapter.setData(users)
            }
        }
    }

    private fun setupRecyclerView() = binding.rvUsers.apply {
        userAdapter = UserAdapter()
        adapter = userAdapter
        layoutManager = LinearLayoutManager(this@FavoriteActivity)
        setHasFixedSize(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvUsers.adapter = null
    }

}