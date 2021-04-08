package com.rasyidin.githubapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidin.githubapp.core.adapter.UserAdapter
import com.rasyidin.githubapp.core.data.Resource
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.databinding.FragmentFollowersFollowingBinding
import com.rasyidin.githubapp.ui.detail.DetailActivity.Companion.EXTRA_DATA
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowersFollowingFragment : Fragment() {

    companion object {
        private const val ARG_SECTION_INDEX = "section_index"

        fun newInstance(index: Int): FollowersFollowingFragment {
            val fragment = FollowersFollowingFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_INDEX, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    private val viewModel: DetailViewModel by sharedViewModel()

    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            username = activity?.intent?.getStringExtra(EXTRA_DATA)

            setupRecyclerView()

            setupViewPager()
        }
    }

    private fun setupViewPager() {
        var index: Int? = 0
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_INDEX, 0)
        }
        when (index) {
            0 -> observeFollowers()
            else -> observeFollowing()
        }
    }

    private fun observeFollowing() {
        viewModel.getUserFollowing(username)
        viewModel.followingUser.observe(viewLifecycleOwner) { resource ->
            showUserFollowersFollowing(resource)
        }
    }

    private fun observeFollowers() {
        viewModel.getUserFollowers(username)
        viewModel.followersUser.observe(viewLifecycleOwner) { resource ->
            showUserFollowersFollowing(resource)
        }
    }

    private fun showUserFollowersFollowing(resource: Resource<List<User>>) {
        when (resource) {
            is Resource.Success -> {
                binding.loading.visibility = View.GONE
                binding.lottieNoData.visibility = View.GONE
                resource.data?.let { users ->
                    userAdapter.setData(users)
                }
            }
            is Resource.Error -> {
                binding.loading.visibility = View.GONE
                binding.lottieNoData.visibility = View.VISIBLE
            }
            is Resource.Loading -> {
                binding.loading.visibility = View.VISIBLE
                binding.lottieNoData.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() = binding.rvUsers.apply {
        userAdapter = UserAdapter()
        layoutManager = LinearLayoutManager(activity)
        adapter = userAdapter
        setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvUsers.adapter = null
        _binding = null
    }

}