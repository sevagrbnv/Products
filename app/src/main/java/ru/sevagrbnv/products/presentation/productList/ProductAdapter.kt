package ru.sevagrbnv.products.presentation.productList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sevagrbnv.products.R
import ru.sevagrbnv.products.databinding.ProductItemListBinding
import ru.sevagrbnv.products.domain.model.Product

class ProductAdapter(
    private val context: Context,
    private val listener: OnItemClickListener
) :
    PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(CinemaComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(context, binding, listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class ProductViewHolder(
        private val context: Context,
        private val binding: ProductItemListBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            Picasso.get().load(product.thumbnail)
                .placeholder(R.drawable.placeholder_ic)
                .into(binding.thumbnail)

            binding.title.text = product.title
            binding.description.text = product.description
            binding.brand.text = product.brand
            binding.rating.text = context.getString(R.string.rating_value, product.rating.toString())
            binding.price.text = product.price.toString()
            binding.discount.text = context.getString(R.string.discount_value, product.discount.toString())

            binding.root.setOnClickListener {
                listener.onItemClick(product)
            }
        }
    }

    object CinemaComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}