package com.erictel.bottomdynamic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.setPadding
import androidx.core.view.size
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.erictel.bottomdynamic.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val menuItemIndex = 0

        val bottomNavigationMenuView = binding.navView.getChildAt(0) as BottomNavigationMenuView
        val bottomNavigationItemView = bottomNavigationMenuView.getChildAt(menuItemIndex) as BottomNavigationItemView

        val iconContainer = bottomNavigationItemView.getChildAt(0) as ViewGroup
        setRoundedCorners(iconContainer, false)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener(object : OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val bottomNavigationMenuView = binding.navView.getChildAt(0) as BottomNavigationMenuView
                val bottomNavigationItemView = bottomNavigationMenuView.getChildAt(getPosition(item)) as BottomNavigationItemView

                val iconContainer = bottomNavigationItemView.getChildAt(0) as ViewGroup
                setRoundedCorners(iconContainer, false) // 20f es el radio de las esquinas en dp

                return true
            }
        })
    }

    private fun getPosition(item: MenuItem): Int {
        val position = when (item.itemId) {
            R.id.navigation_home -> 0
            R.id.navigation_dashboard -> 1
            R.id.navigation_notifications -> 2
            // Añade más casos según la cantidad de elementos en tu BottomNavigationView
            else -> -1
        }
        changeNoSelected(position)
        return position
    }

    @SuppressLint("RestrictedApi")
    private fun changeNoSelected(position: Int) {
        for (i in 0 until binding.navView.menu.size){
            if(i!= position){
                val bottomNavigationMenuView = binding.navView.getChildAt(0) as BottomNavigationMenuView
                val bottomNavigationItemView = bottomNavigationMenuView.getChildAt(i) as BottomNavigationItemView

                val iconContainer = bottomNavigationItemView.getChildAt(0) as ViewGroup
                setRoundedCorners(iconContainer, true) // 20f es el radio de las esquinas en dp
            }
        }

    }

    private fun setRoundedCorners(viewGroup: ViewGroup, isWhite: Boolean) {
        // Convierte el radio de las esquinas de dp a píxeles
        val radiusPx = dpToPx(16f, this)

        // Crea un Drawable con esquinas redondeadas
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = radiusPx
        gradientDrawable.setColor(if(isWhite) ContextCompat.getColor(this@MainActivity,R.color.white)else ContextCompat.getColor(this@MainActivity,R.color.morado)) // Color del fondo

        // Establece el Drawable como fondo del ViewGroup
        viewGroup.background = gradientDrawable
        viewGroup.setPadding(10,10,10,10)
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }
}