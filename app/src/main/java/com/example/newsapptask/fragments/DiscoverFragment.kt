/**
 * DiscoverFragment displays a list of news articles with sorting options and a search feature.
 * It observes the newsList LiveData from the NewsViewModel and updates the RecyclerView adapter accordingly.
 * The fragment also handles sorting the news articles by date and filtering them based on the search query.
 * Additionally, it observes the visibility of the soft keyboard and hides/shows the bottom navigation accordingly.
 */

package com.example.newsapptask.fragments

import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptask.MainActivity
import com.example.newsapptask.adapter.NewsAdapterRV
import com.example.newsapptask.databinding.FragmentDiscoverBinding
import com.example.newsapptask.viewModel.NewsViewModel

class DiscoverFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapterRV
    private lateinit var binding: FragmentDiscoverBinding
    private var isKeyboardVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverBinding.inflate(layoutInflater)
        if(!isConnectivityAvailable())
            Toast.makeText(requireContext(),"Please connect to Internet",Toast.LENGTH_LONG).show()

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeNewsList()
        setupSortOptions()
        fetchNews()
        setupSearchView()
        observeKeyboardVisibility()
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapterRV(emptyList(), requireContext())
        binding.recyclerView.apply {
            adapter = this@DiscoverFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeNewsList() {
        viewModel.newsList.observe(viewLifecycleOwner, Observer { newsList ->
            adapter.updateData(newsList)
            binding.progress.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            // Handle error
        })
    }

    private fun setupSortOptions() {
        binding.chipNew.setOnClickListener { filterByNew() }
        binding.chipOld.setOnClickListener { filterByOld() }
    }

    private fun fetchNews() {
        binding.progress.visibility = View.VISIBLE
        viewModel.fetchNews()
    }

    private fun filterByNew() {
        val filteredList = viewModel.newsList.value?.sortedByDescending { it.publishedAt }
        adapter.updateData(filteredList ?: emptyList())
    }

    private fun filterByOld() {
        val filteredList = viewModel.newsList.value?.sortedBy { it.publishedAt }
        adapter.updateData(filteredList ?: emptyList())
    }

    private fun setupSearchView() {


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.ivClear.visibility = if (s.isNullOrBlank()) View.GONE else View.VISIBLE
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.ivClear.setOnClickListener {
            binding.etSearch.text.clear()
        }
    }

    private fun filter(query: String) {
        adapter.filter(query)
    }

    private fun observeKeyboardVisibility() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            if (isAdded) { // Check if the fragment is attached
                val r = Rect()
                binding.root.getWindowVisibleDisplayFrame(r)
                val screenHeight = binding.root.rootView.height
                val keypadHeight = screenHeight - r.bottom
                isKeyboardVisible = keypadHeight > screenHeight * 0.15
                if (isKeyboardVisible) {
                    (requireActivity() as MainActivity).hideBottomNavigation()
                } else {
                    (requireActivity() as MainActivity).showBottomNavigation()
                }
            }
        }
    }
    private fun isConnectivityAvailable(): Boolean {
        var activeNetwork: NetworkInfo? = null
        try {
            val connectivityManager =
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            activeNetwork = connectivityManager.activeNetworkInfo
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return !(activeNetwork == null || !activeNetwork.isConnectedOrConnecting)
    }
}
