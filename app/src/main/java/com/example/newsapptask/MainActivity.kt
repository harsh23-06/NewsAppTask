
/**
 * MainActivity is the main activity of the NewsAppTask application.
 * It hosts various fragments and manages navigation between them.
 *
 * The activity checks for internet connectivity upon creation. If the device
 * is connected to the internet, it displays the HomeFragment. Otherwise, it
 * shows a TextView indicating the need to connect to the internet and displays
 * a toast message.
 *
 * The activity also listens for bottom navigation item selections and replaces
 * the fragment container accordingly.
 *
 * @property binding The view binding instance for the activity layout.
 */

package com.example.newsapptask

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptask.databinding.ActivityMainBinding
import com.example.newsapptask.fragments.DiscoverFragment
import com.example.newsapptask.fragments.HomeFragment
import com.example.newsapptask.viewModel.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(isConnectivityAvailable())
            replaceFragment(HomeFragment())
        else{
            Toast.makeText(this,"Please connect to Internet",Toast.LENGTH_LONG).show()


        }
        //initially HomeFragment must be displayed

        if(savedInstanceState==null)
            replaceFragment(HomeFragment())
        binding.bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.collection -> {
                    replaceFragment(DiscoverFragment())
                    true
                }
                R.id.home->{
                    replaceFragment(HomeFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
    fun hideBottomNavigation() {
        binding.bottomNavBar.visibility = View.GONE
    }
    fun showBottomNavigation() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }
    private fun isConnectivityAvailable(): Boolean {
        var activeNetwork: NetworkInfo? = null
        try {
            val connectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            activeNetwork = connectivityManager.activeNetworkInfo
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return !(activeNetwork == null || !activeNetwork.isConnectedOrConnecting)
    }
}



