package com.rasyidin.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.adapter.UserAdapter
import com.rasyidin.githubapp.core.data.source.Resource
import com.rasyidin.githubapp.databinding.ActivityMainBinding
import com.rasyidin.githubapp.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

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

        setupRecyclerView()

        onItemClick()

        subscribeToObserver()

        navigateToSetting()

        searchUser()
    }

    private fun setupRecyclerView() = binding.rvUsers.apply {
        userAdapter = UserAdapter()
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = userAdapter
        setHasFixedSize(true)
    }

    private fun onItemClick() {
        userAdapter.onItemClick = { selectedItem ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedItem)
            startActivity(intent)
        }
    }

    private fun searchUser() {
        binding.searchUser.setOnClickListener {
            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeToObserver() {
        viewModel.getUsers().observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        userAdapter.setData(it)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Message: ${resource.message}")
                }
                is Resource.Loading -> {
                }
            }
        }

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
            Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}