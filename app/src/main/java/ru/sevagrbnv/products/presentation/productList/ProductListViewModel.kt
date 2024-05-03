package ru.sevagrbnv.products.presentation.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sevagrbnv.products.domain.model.Product
import ru.sevagrbnv.products.domain.model.usecase.GetPagingProductsUseCase
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getPagingProductsUseCase: GetPagingProductsUseCase
) : ViewModel() {

    private val _data = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val data: StateFlow<PagingData<Product>>
        get() = _data

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            getPagingProductsUseCase.invoke()
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _data.value = pagingData
                }
        }
    }
}