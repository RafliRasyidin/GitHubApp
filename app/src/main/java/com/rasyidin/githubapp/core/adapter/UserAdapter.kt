package com.rasyidin.githubapp.core.adapter

import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.core.utils.loadImage
import com.rasyidin.githubapp.databinding.ItemListUserBinding

class UserAdapter : BaseAdapter<User>(R.layout.item_list_user) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val user = data[position]
        val binding = ItemListUserBinding.bind(holder.itemView)
        with(binding) {
            imgUser.loadImage(user.avatar, R.drawable.ic_github, R.drawable.ic_broken_image)
            tvUsername.text = user.username
            tvType.text = user.type

            root.setOnClickListener {
                onItemClickListener?.invoke(user)
            }

        }

    }
}