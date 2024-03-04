

/**
 * HomeFragment displays a list of news articles in both horizontal and vertical RecyclerViews.
 * It observes the newsList LiveData from the NewsViewModel and updates the RecyclerView adapters accordingly.
 * The fragment also handles the loading of news data and displays progress bars while fetching data.
 */

package com.example.newsapptask.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptask.adapter.NewsFragmentAdapter
import com.example.newsapptask.databinding.FragmentHomeBinding
import com.example.newsapptask.viewModel.NewsViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var adapter: NewsFragmentAdapter
    private lateinit var adapter1: NewsFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        if(!isConnectivityAvailable())
            Toast.makeText(requireContext(),"Please connect to Internet", Toast.LENGTH_LONG).show()

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeNewsList()
        fetchNews()
    }

    private fun setupRecyclerViews() {
        adapter = NewsFragmentAdapter(emptyList(), requireContext(), true)
        binding.horizontalRV.adapter = adapter

        adapter1 = NewsFragmentAdapter(emptyList(), requireContext(), false)
        binding.verticalRV.adapter = adapter1
    }

    private fun observeNewsList() {
        newsViewModel.newsList.observe(viewLifecycleOwner, Observer { newsList ->
            adapter.updateData(newsList)
            adapter1.updateData(newsList)
            binding.progressBarHorizontal.visibility = View.GONE // Hide horizontal ProgressBar
            binding.progressBarVertical.visibility = View.GONE // Hide vertical ProgressBar
        })

        newsViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            // Handle error
            binding.progressBarHorizontal.visibility = View.GONE // Hide horizontal ProgressBar on error
            binding.progressBarVertical.visibility = View.GONE // Hide vertical ProgressBar on error
        })
    }

    private fun fetchNews() {
        // Show horizontal ProgressBar when fetching news
        binding.progressBarHorizontal.visibility = View.VISIBLE
        // Show vertical ProgressBar when fetching news
        binding.progressBarVertical.visibility = View.VISIBLE
        newsViewModel.fetchNews()
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
