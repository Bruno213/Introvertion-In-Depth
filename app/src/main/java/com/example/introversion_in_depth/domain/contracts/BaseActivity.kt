package com.example.introversion_in_depth.domain.contracts

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.example.introversion_in_depth.util.LanguageConfig

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LanguageConfig.clearResources()
    }
}