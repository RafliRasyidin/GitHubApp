package com.rasyidin.githubapp.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rasyidin.githubapp.R
import com.rasyidin.githubapp.core.domain.model.User
import com.rasyidin.githubapp.databinding.ItemListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users = ArrayList<User>()

    fun setData(user: List<User>) {
        if (user.isNullOrEmpty()) return
        users.clear()
        users.addAll(user)
        notifyDataSetChanged()
    }

    var onItemClick: ((User) -> Unit)? = null

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListUserBinding.bind(itemView)
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(
                        itemView.context.resources.getIdentifier(
                            user.avatar,
                            "drawable",
                            itemView.context.packageName
                        )
                    )
                    .into(imgUser)

                tvUsername.text = user.username
                tvName.text = user.name
                tvCompany.text = user.company

            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(users[adapterPosition])
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = users[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return users.size
    }
}