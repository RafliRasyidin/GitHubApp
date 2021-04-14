package com.rasyidin.consumerapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidin.consumerapp.adapter.UserAdapter
import com.rasyidin.consumerapp.databinding.ActivityFavoriteBinding
import com.rasyidin.consumerapp.utils.Constants.AUTHORITY
import com.rasyidin.consumerapp.utils.Constants.SCHEME
import com.rasyidin.consumerapp.utils.Constants.TABLE_FAVORITE
import com.rasyidin.consumerapp.utils.cursorToArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var userAdapter: UserAdapter

    private val contentUri: Uri = Uri.Builder()
        .scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_FAVORITE)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        loadData()
    }

    private fun setupRecyclerView() = binding.rvFavorite.apply {
        userAdapter = UserAdapter()
        adapter = userAdapter
        layoutManager = LinearLayoutManager(this@FavoriteActivity)
        setHasFixedSize(true)
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.Main) {
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(contentUri, null, null, null, null, null)
                cursorToArrayList(cursor)
            }

            val favorite = deferredFavorite.await()
            if (favorite.size > 0) {
                userAdapter.setData(favorite)
                binding.lottieEmptyData.visibility = View.GONE
                binding.rvFavorite.visibility = View.VISIBLE
            } else {
                userAdapter.setData(emptyList())
                binding.lottieEmptyData.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvFavorite.adapter = null
    }
}