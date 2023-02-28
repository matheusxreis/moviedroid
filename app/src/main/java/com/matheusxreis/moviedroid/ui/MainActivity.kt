package com.matheusxreis.moviedroid.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.matheusxreis.moviedroid.R
import com.matheusxreis.moviedroid.models.MoviePoster
import com.matheusxreis.moviedroid.ui.fragments.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Moviedroid)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = findNavController(R.id.my_nav_host)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.myListsFragments2
            )
        )
        bottomBar.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val value = intent.getStringExtra("key")
        if (value != null) {
            try {
                val movie = Gson().fromJson(value, MoviePoster::class.java)
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    movie = movie,
                    fromSearch = true
                )

                navController.navigate(action)
            }catch(e:Exception){
                Log.d("MainActivityException", e.toString())
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}