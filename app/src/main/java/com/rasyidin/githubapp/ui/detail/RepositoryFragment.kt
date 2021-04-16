package com.rasyidin.githubapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidin.githubapp.core.adapter.RepositoryAdapter
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.Repository
import com.rasyidin.githubapp.databinding.FragmentRepositoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryFragment : Fragment() {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null

    private val viewModel: DetailViewModel by viewModel()

    private lateinit var repositoryAdapter: RepositoryAdapter

    companion object {
        private const val ARG_USERNAME = "arg_username"

        fun newInstance(username: String?) =
            RepositoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            username = arguments?.getString(ARG_USERNAME)

            setupRecyclerView()

            subscribeToObserver()

            onItemClicked()
        }
    }

    private fun setupRecyclerView() = binding.rvRepository.apply {
        repositoryAdapter = RepositoryAdapter()
        adapter = repositoryAdapter
        layoutManager = LinearLayoutManager(activity)
        setHasFixedSize(true)
    }

    private fun subscribeToObserver() {
        viewModel.getUserRepository(username)
        viewModel.repositoryUser.observe(viewLifecycleOwner) { resource ->
            showRepositories(resource)
        }
    }

    private fun showRepositories(resource: Resource<List<Repository>>) {
        when (resource) {
            is Resource.Success -> {
                binding.loading.visibility = View.GONE
                binding.rvRepository.visibility = View.VISIBLE
                binding.errorContainer.root.visibility = View.GONE
                if (resource.data.isNullOrEmpty()) {
                    binding.lottieNoData.visibility = View.VISIBLE
                } else {
                    repositoryAdapter.setData(resource.data)
                    binding.lottieNoData.visibility = View.GONE
                }

            }
            is Resource.Error -> {
                binding.loading.visibility = View.GONE
                binding.rvRepository.visibility = View.GONE
                binding.lottieNoData.visibility = View.GONE
                binding.errorContainer.root.visibility = View.VISIBLE
            }
            is Resource.Loading -> {
                binding.loading.visibility = View.VISIBLE
                binding.rvRepository.visibility = View.GONE
                binding.lottieNoData.visibility = View.GONE
                binding.errorContainer.root.visibility = View.GONE
            }
        }
    }

    private fun onItemClicked() {
        repositoryAdapter.onItemClickListener = { selectedItem ->
            val url: Uri? = selectedItem.reposUrl?.toUri()
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }
    }
}