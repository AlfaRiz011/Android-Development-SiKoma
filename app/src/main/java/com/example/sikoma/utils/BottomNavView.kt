package com.example.sikoma.utils

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.bottomnavigation.BottomNavigationView

object BottomNavView {
    /**
     * Menyembunyikan BottomNavigationView dengan animasi dan mengubah constraint
     * container agar fragment container mengisi ruang yang tersisa.
     *
     * @param bottomNav BottomNavigationView yang akan disembunyikan.
     * @param constraintLayout Layout induk (ConstraintLayout) yang berisi fragment container.
     * @param fragmentContainerId ID dari fragment container yang constraint-nya akan diubah.
     * @param duration Durasi animasi (default: 200ms).
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

        bottomNav.postDelayed({
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            // Hubungkan fragment container ke bagian bawah parent agar mengisi ruang kosong.
            constraintSet.connect(
                fragmentContainerId,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(constraintLayout)
        }, duration)
    }

    /**
     * Menampilkan BottomNavigationView dengan animasi dan mengembalikan constraint container
     * agar fragment container berhenti sebelum BottomNavigationView.
     *
     * @param bottomNav BottomNavigationView yang akan ditampilkan.
     * @param constraintLayout Layout induk (ConstraintLayout) yang berisi fragment container.
     * @param fragmentContainerId ID dari fragment container yang constraint-nya akan diubah.
     * @param navViewId ID dari BottomNavigationView (sebagai acuan constraint).
     * @param duration Durasi animasi (default: 200ms).
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
            // Hubungkan fragment container ke bagian atas BottomNavigationView.
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
