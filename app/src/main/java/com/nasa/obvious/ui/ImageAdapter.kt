package com.nasa.obvious.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nasa.obvious.R
import com.nasa.obvious.models.Nasa

class ImageAdapter(private val onClick: (Nasa) -> Unit) :
    ListAdapter<Nasa, ImageAdapter.ImageViewHolder>(NasaDiffCallback) {

    class ImageViewHolder(itemView: View, val onClick: (Nasa) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nasaImageView: ImageView = itemView.findViewById(R.id.ivNasa)
        private var currentNasa: Nasa? = null

        init {
            itemView.setOnClickListener {
                currentNasa?.let { onClick(it) }
            }
        }

        fun bind(nasa: Nasa) {
            currentNasa = nasa
            Glide.with(nasaImageView.context)
                .load(nasa.url)
                .into(nasaImageView);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object NasaDiffCallback : DiffUtil.ItemCallback<Nasa>() {
    override fun areItemsTheSame(oldItem: Nasa, newItem: Nasa): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Nasa, newItem: Nasa): Boolean {
        return oldItem.url == newItem.url
    }
}