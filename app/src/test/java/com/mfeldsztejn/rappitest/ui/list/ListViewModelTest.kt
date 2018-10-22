package com.mfeldsztejn.rappitest.ui.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mfeldsztejn.rappitest.dtos.Movie
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ListViewModelTest {
    lateinit var listViewModel: ListViewModel

    @BeforeEach
    fun beforeEach() {
        listViewModel = spyk()
        listViewModel.moviesPagedList = mockk()
        every { listViewModel["createLiveData"](ofType(String::class), ofType(String::class)) } returns mockk<LiveData<PagedList<Movie>>>()
    }

    @Nested
    inner class SearchTests {

        @Test
        fun `Search with different query, should return true`() {
            val previousLiveData = listViewModel.moviesPagedList

            val searchResult = listViewModel.search("any", "Star Wars")
            val newLiveData = listViewModel.moviesPagedList

            Assertions.assertThat(searchResult).isTrue()
            // This means live data object changed
            Assertions.assertThat(newLiveData).isNotEqualTo(previousLiveData)
        }

        @Test
        fun `Search with same query, should return false`() {
            val previousLiveData = listViewModel.moviesPagedList

            // Use null since its the default
            val searchResult = listViewModel.search("any", null)
            val newLiveData = listViewModel.moviesPagedList

            Assertions.assertThat(searchResult).isFalse()
            Assertions.assertThat(previousLiveData).isEqualTo(newLiveData)
        }
    }

    @Nested
    inner class DiscoverTests {
        @Test
        fun `Calling discover should recreate LiveData every time`() {
            var previousLiveData = mockk<LiveData<PagedList<Movie>>>()
            for (i in 0..5) {
                listViewModel.discover("any")
                Assertions.assertThat(previousLiveData).isNotEqualTo(listViewModel.moviesPagedList)
                previousLiveData = listViewModel.moviesPagedList
            }
        }
    }
}