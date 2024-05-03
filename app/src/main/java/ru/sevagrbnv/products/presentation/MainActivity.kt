package ru.sevagrbnv.products.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.products.R
import ru.sevagrbnv.products.presentation.productDetail.DetailFragment
import ru.sevagrbnv.products.presentation.productList.ProductListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DetailFragment.OpenPrevFragment {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ProductListFragment.newInstance())
                .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }
}