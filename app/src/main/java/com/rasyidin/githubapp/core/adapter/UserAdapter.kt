package com.rasyidin.githubapp.core.adapter

import com.bumptech.glide.Glide
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.databinding.ItemListUserBinding

class UserAdapter : BaseAdapter<User>(R.layout.item_list_user) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val user = data[position]
        val binding = ItemListUserBinding.bind(holder.itemView)
        with(binding) {
            Glide.with(holder.itemView)
                .load(user.avatar)
                .placeholder(R.drawable.ic_github)
                .error(R.drawable.ic_broken_image)
                .into(imgUser)

            tvUsername.text = user.username
            tvType.text = user.name

            root.setOnClickListener {
                onItemClickListener?.invoke(user)
            }

        }

    }
}