package bootcamp.sparta.myapplemarket.data

import bootcamp.sparta.myapplemarket.data.model.Product

object ProductsItemData {
    private val productsList : MutableList<Product> = getDummyData()

    fun getProducts() = productsList
    fun getProducts(id: Int) = productsList.find { it.id == id }
}