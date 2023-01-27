package com.nasa.obvious.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nasa.obvious.R
import com.nasa.obvious.models.Nasa
import com.nasa.obvious.ui.binding.bindNasaImage

class ImageAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Nasa, ImageAdapter.ImageViewHolder>(NasaDiffCallback) {

    class ImageViewHolder(itemView: View, val onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val nasaImageView: ImageView = itemView.findViewById(R.id.ivNasa)

        init {
            itemView.setOnClickListener { onClick(adapterPosition) }
        }

        fun bind(nasa: Nasa) {
            nasaImageView.bindNasaImage(nasa.url)
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