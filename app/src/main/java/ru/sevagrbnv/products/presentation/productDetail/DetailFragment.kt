package ru.sevagrbnv.products.presentation.productDetail

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.products.R
import ru.sevagrbnv.products.databinding.FragmentDetailBinding
import ru.sevagrbnv.products.domain.model.Product

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentDetailBinding.inflate(
            layoutInflater
        )
    }

    private val imageAdapter by lazy { ImageAdapter(checkNotNull(product).images) }

    private var product: Product? = null

    private var openPrevFragment: OpenPrevFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenPrevFragment) {
            openPrevFragment = context
        } else {
            throw RuntimeException("Activity must implement OpenPrevFragment")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_PRODUCT, Product::class.java)
            } else it.getParcelable(ARG_PRODUCT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = checkNotNull(product)

        Picasso.get().load(product.thumbnail)
            .placeholder(R.drawable.placeholder_ic)
            .into(binding.thumbnail)

        binding.backButton.setOnClickListener { openPrevFragment?.back() }

        binding.title.text = product.title
        binding.description.text = product.description
        binding.brand.text = getString(R.string.brand_value, product.brand)
        binding.rating.text = getString(R.string.rating_value, product.rating.toString())
        binding.price.text = getString(R.string.price_value, product.price.toString())
        binding.discount.text = getString(R.string.discount_value, product.discount.toString())
        binding.category.text = getString(R.string.category_value, product.category)

        binding.posterRecView.adapter = imageAdapter
    }

    interface OpenPrevFragment {
        fun back()
    }

    override fun onDetach() {
        super.onDetach()
        openPrevFragment = null
    }

    companion object {
        const val ARG_PRODUCT = "ARG_PRODUCT"

        @JvmStatic
        fun newInstance(product: Product) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT, product)
                }
            }
    }
}