package com.erictel.bottomdynamic


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.RestrictTo
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

@SuppressLint("RestrictedApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class BottomDynamic @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init {
        // Inflar tu diseño personalizado y establecerlo como vista del item

        // Obtener referencias a los elementos en tu diseño

        val menuItemIndex = 0

// Obtiene la vista del elemento del menú
        val bottomNavigationMenuView =
            getChildAt(0) as BottomNavigationMenuView
        val bottomNavigationItemView =
            bottomNavigationMenuView.getChildAt(menuItemIndex) as BottomNavigationItemView

// Intenta personalizar la apariencia del ícono
        val iconContainer = bottomNavigationItemView.getChildAt(0)
        iconContainer.setBackgroundColor(ContextCompat.getColor(context,R.color.black))
    }
}