package com.rasyidin.githubapp.core.adapter

import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.domain.model.Repository
import com.rasyidin.githubapp.core.utils.toShortNumberDisplay
import com.rasyidin.githubapp.databinding.ItemListRepositoryBinding

class RepositoryAdapter : BaseAdapter<Repository>(R.layout.item_list_repository) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val repository = data[position]
        val binding = ItemListRepositoryBinding.bind(holder.itemView)
        with(binding) {
            tvTitle.text = repository.name
            tvDesc.text = repository.description
            tvLanguage.text = repository.language
            tvStarCount.text = repository.starsCount.toShortNumberDisplay()
            tvWatchersCount.text = repository.watchersCount.toShortNumberDisplay()
            tvForkCount.text = repository.forksCount.toShortNumberDisplay()

            root.setOnClickListener {
                onItemClickListener?.invoke(repository)
            }
        }
    }
}