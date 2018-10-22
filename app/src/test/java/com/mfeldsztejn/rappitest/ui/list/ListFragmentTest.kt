package com.mfeldsztejn.rappitest.ui.list

import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.rappitest.R
import com.mfeldsztejn.rappitest.dtos.Movie
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ListFragmentTest {

    @Nested
    @ExtendWith(MockKExtension::class)
    inner class OnCreateView {

        @MockK
        lateinit var listViewModel: ListViewModel

        @MockK
        lateinit var layoutInflater: LayoutInflater

        @InjectMockKs
        lateinit var listFragment: ListFragment

        @BeforeEach
        fun beforeEach() {
            every { listViewModel.discover(any()) } answers { nothing }
            every { layoutInflater.inflate(any() as Int, any(), false) } returns mockk()
            listFragment.sortBy = "sortBy"
        }

        @Test
        fun `onCreateView, should call discover`() {
            listFragment.onCreateView(layoutInflater, mockk(), null)

            verify(exactly = 1) { listViewModel.discover("sortBy") }
        }
    }

    @Nested
    @ExtendWith(MockKExtension::class)
    inner class OnViewCreated {

        private val listFragment = spyk<ListFragment>(recordPrivateCalls = true)
        private val view = mockk<View>()
        private val progressBar = mockk<ProgressBar>(relaxed = true)
        private val recyclerView = mockk<RecyclerView>(relaxed = true)

        @BeforeEach
        fun beforeEach() {
            every { listFragment.view } returns view
            every { listFragment["listenForChanges"]() } answers { nothing }
            every { view.findViewById<ProgressBar>(R.id.progressbar) } returns progressBar
            every { view.findViewById<RecyclerView>(R.id.recyclerView) } returns recyclerView
        }

        @Test
        fun `onViewCreated, should set values`() {
            listFragment.onViewCreated(view, null)

            verify(exactly = 1) { progressBar.visibility = View.VISIBLE }
            verify(exactly = 1) { recyclerView.adapter = ofType(MoviesAdapter::class) }
            verify(exactly = 1) { recyclerView.layoutManager = ofType(LinearLayoutManager::class) }
            verify(exactly = 1) { listFragment["listenForChanges"]() }
        }
    }

    @Nested
    @ExtendWith(MockKExtension::class)
    inner class LogicMethods {
        val viewModel = mockk<ListViewModel>()
        val liveData = mockk<LiveData<PagedList<Movie>>>()

        @InjectMockKs
        var fragment = ListFragment()

        @BeforeEach
        fun beforeEach() {
            clearMocks(liveData, viewModel)

            every { liveData.observe(any(), any()) } answers { nothing }
            every { viewModel.moviesPagedList } returns liveData
        }

        @Test
        fun `Calling search, should redirect to view model and if true register to changes`() {
            every { viewModel.search(any(), any()) } returns true
            fragment.sortBy = "sortBy"
            fragment.search("Star Wars")

            verify(exactly = 1) { viewModel.search("sortBy", "Star Wars") }
            verify(exactly = 1) { liveData.observe(any(), any()) }
        }

        @Test
        fun `Calling search, should redirect to view model and if false do nothing`() {
            every { viewModel.search(any(), any()) } returns false
            fragment.sortBy = "sortBy"
            fragment.search("Star Wars")

            verify(exactly = 1) { viewModel.search("sortBy", "Star Wars") }
            verify(exactly = 0) { liveData.observe(any(), any()) }
        }

        @Test
        fun `Calling clear search, should call discover`() {
            every { viewModel.discover(any()) } answers { nothing }
            fragment.sortBy = "sortBy"
            fragment.clearSearch()

            verify(exactly = 1) { viewModel.discover("sortBy") }
            verify(exactly = 1) { liveData.observe(any(), any()) }
        }
    }


}