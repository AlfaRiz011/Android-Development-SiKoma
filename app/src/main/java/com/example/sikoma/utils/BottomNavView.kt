package com.example.sikoma.utils

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.sikoma.R
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavView {
    /**
     * Menyembunyikan Bottom Navigation dengan animasi dan mengubah constraint container.
     *
     * @param bottomNav BottomNavigationView yang akan disembunyikan.
     * @param constraintLayout Layout induk (ConstraintLayout) tempat container dan bottom nav berada.
     * @param fragmentContainerId ID dari container fragment yang constraint-nya akan diubah. Default: R.id.fragment_container_home.
     * @param duration Durasi animasi dalam milidetik (default: 200).
     */
    fun hide(
        bottomNav: BottomNavigationView,
        constraintLayout: ConstraintLayout,
        fragmentContainerId: Int,
        duration: Long = 200L
    ) {
        bottomNav.animate()
            .translationY(bottomNav.height.toFloat())
            .setDuration(duration)
            .start()

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            fragmentContainerId,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(constraintLayout)
    }

    /**
     * Menampilkan Bottom Navigation dengan animasi dan mengembalikan constraint container.
     *
     * @param bottomNav BottomNavigationView yang akan ditampilkan.
     * @param constraintLayout Layout induk (ConstraintLayout) tempat container dan bottom nav berada.
     * @param fragmentContainerId ID dari container fragment yang constraint-nya akan diubah. Default: R.id.fragment_container_home.
     * @param navViewId ID dari BottomNavigationView sebagai referensi constraint. Default: R.id.nav_view.
     * @param duration Durasi animasi dalam milidetik (default: 200).
     */
    fun show(
        bottomNav: BottomNavigationView,
        constraintLayout: ConstraintLayout,
        fragmentContainerId: Int,
        navViewId: Int,
        duration: Long = 200L
    ) {
        bottomNav.animate()
            .translationY(0f)
            .setDuration(duration)
            .start()

        bottomNav.postDelayed({
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(
                fragmentContainerId,
                ConstraintSet.BOTTOM,
                navViewId,
                ConstraintSet.TOP
            )
            constraintSet.applyTo(constraintLayout)
        }, duration)
    }
}
