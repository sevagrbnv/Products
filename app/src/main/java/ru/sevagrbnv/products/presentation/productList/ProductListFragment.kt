package ru.sevagrbnv.products.presentation.productList

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.sevagrbnv.products.R
import ru.sevagrbnv.products.databinding.FragmentProductListBinding
import ru.sevagrbnv.products.domain.model.Product
import ru.sevagrbnv.products.presentation.productDetail.DetailFragment
import java.net.UnknownHostException

@AndroidEntryPoint
class ProductListFragment : Fragment(), OnItemClickListener {

    private val viewModel by viewModels<ProductListViewModel>()

    private val binding by lazy { FragmentProductListBinding.inflate(layoutInflater) }
    private val cinemaAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ProductAdapter(
            requireContext(),
            this
        )
    }
    private val loaderStateAdapter by lazy(LazyThreadSafetyMode.NONE) { ProductListLoaderStateAdapter { cinemaAdapter.retry() } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = cinemaAdapter.withLoadStateFooter(footer = loaderStateAdapter)
            setHasFixedSize(true)
        }

        cinemaAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Error -> throw UnknownHostException()
                LoadState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                }

                is LoadState.NotLoading -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = true
                }
            }
        }

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            try {
                viewModel.data.collectLatest { data ->
                    if (isNetworkAvailable(requireView().context))
                        cinemaAdapter.submitData(data)
                    else {
                        binding.progressBar.isVisible = false
                        Snackbar.make(
                            requireView(),
                            getString(R.string.network_error),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: HttpException) {
                binding.progressBar.isVisible = false
                Snackbar.make(
                    requireView(),
                    "HTTP Error: ${e.code()}",
                    Snackbar.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                binding.progressBar.isVisible = false
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Error: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    override fun onItemClick(product: Product) {
        val fragment = DetailFragment.newInstance(product)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {

        fun newInstance() = ProductListFragment()
    }
}

interface OnItemClickListener {
    fun onItemClick(product: Product)
}