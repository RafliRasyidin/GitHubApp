package com.rasyidin.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.adapter.UserAdapter
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.databinding.ActivityMainBinding
import com.rasyidin.githubapp.ui.detail.DetailActivity
import com.rasyidin.githubapp.ui.favorite.FavoriteActivity
import com.rasyidin.githubapp.ui.setting.SettingActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var userAdapter: UserAdapter

    private lateinit var behaviour: BottomSheetBehavior<*>

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomSheet()

        setupUserRecyclerView()

        onItemClick()

        searchUser()

        subscribeToObserver()

        navigateToSetting()

        navigateToFavorite()

    }

    private fun setupUserRecyclerView() = binding.rvUsers.apply {
        userAdapter = UserAdapter()
        adapter = userAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
        setHasFixedSize(true)
    }

    private fun onItemClick() {
        userAdapter.onItemClickListener = { selectedItem ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedItem.username)
            startActivity(intent)
        }
    }

    private fun searchUser() {
        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(newText)
                }
                return true
            }
        })
    }

    private fun subscribeToObserver() {
        viewModel.getUsers.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    showSuccess(resource)
                }
                is Resource.Error -> {
                    showError(resource)
                }
                is Resource.Loading -> {
                    showLoading()
                }
            }
        }

        viewModel.searchUsers.observe(this) { user ->
            lifecycleScope.launch {
                user.observe(this@MainActivity) { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            showSuccess(resource)
                        }
                        is Resource.Error -> {
                            showError(resource)
                        }
                        is Resource.Loading -> {
                            showLoading()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.rvUsers.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
        binding.lottieNoData.visibility = View.GONE
        binding.errorContainer.root.visibility = View.GONE
    }

    private fun showSuccess(resource: Resource<List<User>>) {
        binding.loading.visibility = View.GONE
        binding.errorContainer.root.visibility = View.GONE

        if (resource.data.isNullOrEmpty()) {
            binding.lottieNoData.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
            Toast.makeText(
                this@MainActivity,
                resources.getString(R.string.not_found),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            resource.data.let {
                userAdapter.setData(it)
            }
            binding.lottieNoData.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }

    private fun showError(resource: Resource<List<User>>) {
        binding.loading.visibility = View.GONE
        binding.lottieNoData.visibility = View.GONE
        binding.rvUsers.visibility = View.GONE
        binding.errorContainer.root.visibility = View.VISIBLE
        Toast.makeText(
            this@MainActivity,
            resources.getString(R.string.error),
            Toast.LENGTH_SHORT
        ).show()
        Log.e(TAG, "Message: ${resource.message}")
    }

    private fun setupBottomSheet() {
        behaviour = BottomSheetBehavior.from(binding.bottomSheet)
        behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvUsers.adapter = null
    }

    private fun navigateToSetting() {
        binding.imgSetting.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToFavorite() {
        binding.imgFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }
}