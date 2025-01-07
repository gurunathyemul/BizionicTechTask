package com.example.bizionictechtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bizionictechtask.R
import com.example.data.model.PostList

class PostWMAdapter : RecyclerView.Adapter<PostWMAdapter.PostViewHolder>() {
    private lateinit var postList: PostList

    fun updateData(posts: PostList) {
        postList = posts
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val bodyTextView: TextView = itemView.findViewById(R.id.bodyTextView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        postList.postList[position].let {
            holder.titleTextView.text = it.title
            holder.bodyTextView.text = it.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.postList.size
    }

    companion object {
        private const val TAG = "PostWMAdapter"
    }
}
