package com.erictel.bottomdynamic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.erictel.bottomdynamic.utils.setupWithNavController as setupWithNavControllerUtil

@SuppressLint("RestrictedApi")
class BottomNavDynamic @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BottomNavigationView(context, attrs) {

    init {
        if (menu.size > 0) {
            val menuView = getChildAt(0) as BottomNavigationMenuView
            moveIndicator(menuView, 0)
        }
    }

    fun setupWithNavController(
        navController: NavController,
        onItemSelected: OnItemSelectedListener = OnItemSelectedListener { true },
    ) {
        setupWithNavControllerUtil(navController) { menuItem: MenuItem ->
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val position = getPosition(menuItem)
            moveIndicator(menuView, position)
            onItemSelected.onNavigationItemSelected(menuItem)
        }
    }

    private fun moveIndicator(
        menuView: BottomNavigationMenuView,
        position: Int
    ) {
        changeNoSelected(position)
        val itemView = menuView.getChildAt(position) as BottomNavigationItemView
        val iconContainer = itemView.getChildAt(0) as ViewGroup
        setRoundedCorners(iconContainer, false) // 20f es el radio de las esquinas en dp
    }

    private fun changeNoSelected(position: Int) {
        for (i in 0 until menu.size) {
            if (i != position) {
                val menuView = getChildAt(0) as BottomNavigationMenuView
                val itemView = menuView.getChildAt(i) as BottomNavigationItemView
                val iconContainer = itemView.getChildAt(0) as ViewGroup
                setRoundedCorners(iconContainer, true) // 20f es el radio de las esquinas en dp
            }
        }
    }

    private fun getPosition(item: MenuItem): Int {
        return when (item.itemId) {
            R.id.navigation_home -> 0
            R.id.navigation_dashboard -> 1
            R.id.navigation_notifications -> 2
            // Añade más casos según la cantidad de elementos en tu BottomNavigationView
            else -> -1
        }
    }

    private fun setRoundedCorners(viewGroup: ViewGroup, isWhite: Boolean) {
        // Convierte el radio de las esquinas de dp a píxeles
        val radiusPx = dpToPx(16f, context)

        // Crea un Drawable con esquinas redondeadas
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = radiusPx
        gradientDrawable.setColor(
            ContextCompat.getColor(
                context,
                if (isWhite) R.color.white else R.color.morado
            )
        ) // Color del fondo

        // Establece el Drawable como fondo del ViewGroup
        viewGroup.background = gradientDrawable
        viewGroup.setPadding(10, 10, 10, 10)
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }
}