package ru.sevagrbnv.products.presentation.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.sevagrbnv.products.databinding.ItemLoadStateBinding

class ProductListLoaderStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ProductListLoaderStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.retryButton.setOnClickListener { retry.invoke() }
            renderState(loadState)
        }

        private fun renderState(loadState: LoadState)  = when (loadState) {
            is LoadState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            }
            is LoadState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = loadState.error.localizedMessage
            }
            is LoadState.NotLoading -> {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            }
        }
    }
}