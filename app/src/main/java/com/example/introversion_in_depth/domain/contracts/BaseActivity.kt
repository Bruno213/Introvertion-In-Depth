package com.example.introversion_in_depth.domain.contracts

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {
    private var _binding: VB? = null

    protected val binding get() = _binding as VB

    abstract val bindingInflater:(LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)

        setContentView(requireNotNull(_binding).root)
        setup()
    }

    abstract fun setup()

    fun toggleDrawer() {
        if((binding.root as DrawerLayout).isDrawerOpen(GravityCompat.START))
            (binding.root as DrawerLayout).closeDrawer(GravityCompat.START)
        else
            (binding.root as DrawerLayout).openDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}