package ru.sevagrbnv.products.presentation.productDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sevagrbnv.products.R
import ru.sevagrbnv.products.databinding.ImageListItemBinding

class ImageAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(private val binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            Picasso.get().load(data)
                .placeholder(R.drawable.placeholder_ic)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
