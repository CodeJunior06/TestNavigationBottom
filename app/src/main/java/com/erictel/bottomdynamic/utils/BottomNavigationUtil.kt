package com.erictel.bottomdynamic.utils

import android.os.Bundle
import androidx.core.view.forEach
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigationrail.NavigationRailView
import java.lang.ref.WeakReference

/**
 * Copy of [androidx.navigation.ui.setupWithNavController]
 *
 * Sets up a [NavigationBarView] for use with a [NavController]. This will call
 * [android.view.MenuItem.onNavDestinationSelected] when a menu item is selected.
 *
 * [NavigationBarView.OnItemSelectedListener] will be notified when a menu item is selected.
 *
 * The selected item in the NavigationView will automatically be updated when the destination
 * changes.
 */
fun NavigationBarView.setupWithNavController(
    navController: NavController,
    onItemSelectedListener: NavigationBarView.OnItemSelectedListener
) {
    NavigationUI.setupWithNavController(this, navController, onItemSelectedListener)
}

/**
 * Copy of [NavigationUI.setupWithNavController]
 *
 * Sets up a [NavigationBarView] for use with a [NavController]. This will call
 * [onNavDestinationSelected] when a menu item is selected. The
 * selected item in the NavigationBarView will automatically be updated when the destination
 * changes.
 *
 * Destinations that implement [androidx.navigation.FloatingWindow] will be ignored.
 *
 * @param navigationBarView The NavigationBarView ([BottomNavigationView] or
 * [NavigationRailView])
 * that should be kept in sync with changes to the NavController.
 * @param navController The NavController that supplies the primary menu.
 * Navigation actions on this NavController will be reflected in the
 * selected item in the NavigationBarView.
 * @param onItemSelectedListener The listener to be notified when a menu item is selected.
 */
fun NavigationUI.setupWithNavController(
    navigationBarView: NavigationBarView,
    navController: NavController,
    onItemSelectedListener: NavigationBarView.OnItemSelectedListener
) {
    navigationBarView.setOnItemSelectedListener { item ->
        onItemSelectedListener.onNavigationItemSelected(item)
        onNavDestinationSelected(
            item,
            navController
        )
    }
    val weakReference = WeakReference(navigationBarView)
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                if (destination is FloatingWindow) {
                    return
                }
                view.menu.forEach { item ->
                    if (destination.hierarchy.any { it.id == item.itemId }) {
                        item.isChecked = true
                    }
                }
            }
        }
    )
}