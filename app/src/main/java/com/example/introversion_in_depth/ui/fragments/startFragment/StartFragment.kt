package com.example.introversion_in_depth.ui.fragments.startFragment

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.introversion_in_depth.Util.removeLinksUnderline
import com.example.introversion_in_depth.base.BaseFragment
import com.example.introversion_in_depth.databinding.FragmentStartBinding
import com.example.introversion_in_depth.ui.MainActivity

class StartFragment: BaseFragment<FragmentStartBinding>(), View.OnClickListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStartBinding
        get() = FragmentStartBinding::inflate

    private val viewModel by viewModels<StartViewModel>()

    override fun setup() {
        setupListeners()
        enableLink()
    }

    override fun onClick(v: View) {
        when(v.id) {
            binding.dehaze.id -> {
                (activity as MainActivity).toggleDrawer()
            }
        }
    }

    private fun setupListeners() {
        binding.dehaze.setOnClickListener(this)
    }

    private fun enableLink() {
        binding.linkToArticle.movementMethod = LinkMovementMethod.getInstance()
        binding.linkToArticle.removeLinksUnderline()
    }
}