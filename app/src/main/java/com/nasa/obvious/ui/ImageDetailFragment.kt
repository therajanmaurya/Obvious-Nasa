package com.nasa.obvious.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nasa.obvious.databinding.FragmentImageDetailBinding
import com.nasa.obvious.models.Nasa
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailFragment(val nasa: Nasa) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentImageDetailBinding.inflate(inflater, container, false).apply {
            this.nasa = this@ImageDetailFragment.nasa
        }.root
    }
}