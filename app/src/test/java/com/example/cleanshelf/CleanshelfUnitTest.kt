package com.example.cleanshelf

import com.example.cleanshelf.data.remote.Cleanshelf
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.data.repository.ProductsRepositoryImpl
import com.example.cleanshelf.util.Resource
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class CleanshelfUnitTest {
    //Creates a fake for the API
    private val mock: Cleanshelf = mockk()

    //late initializes a variable

    private lateinit var repository: ProductsRepositoryImpl

    //To be run before each test
    @Before
    fun setUp() {
        repository = ProductsRepositoryImpl(mock)
    }

    @Test
    fun `getAllProducts returns success when API call succeed`() = runTest {


        val mockProducts = listOf(
            ProductResponseItem(
                "electronics",
                " Smart energy savings SmartThings AI Energy Mode Monitor and reduce the washer.",
                2,
                "https://cdn.mafrservices.com/pim-content/KEN/media/product/241580/1741946403/241580_main.jpg?im=Resize=(60,60)",
                "Samsung Washing Machine",
                23,
                23000,
                "34"
            ),
            ProductResponseItem(
                "electronics",
                " Smart energy savings SmartThings AI Energy Mode Monitor and reduce the washer.",
                2,
                "https://cdn.mafrservices.com/pim-content/KEN/media/product/241580/1741946403/241580_main.jpg?im=Resize=(60,60)",
                "Samsung Washing Machine",
                23,
                23000,
                "34"
            )


        )
        coEvery {
            mock.getAllProducts()
        } returns mockProducts

        val results = repository.getAllProducts()
        Truth.assertThat(results).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((results as Resource.Success).data).hasSize(2)


    }


    @Test
    fun `getAllProducts returns success when API calls fails` () = runTest {

        coEvery { mock.getAllProducts() } throws RuntimeException("Network Error")


        val result = repository.getAllProducts()

        Truth.assertThat(result).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((result as Resource.Error).message).contains("Network Error")
    }





}

