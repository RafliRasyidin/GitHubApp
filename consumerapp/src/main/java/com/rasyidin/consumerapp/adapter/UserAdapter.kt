package com.rasyidin.consumerapp.adapter

import com.rasyidin.consumerapp.R
import com.rasyidin.consumerapp.databinding.ItemListUserBinding
import com.rasyidin.consumerapp.model.User
import com.rasyidin.consumerapp.utils.loadImage

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