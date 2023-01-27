package com.nasa.obvious.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nasa.obvious.databinding.FragmentViewPagerBinding
import com.nasa.obvious.ui.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<ImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter =
            ViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle).apply {
                viewModel.images.value?.first?.forEach {
                    addFragment(ImageDetailFragment(it))
                }
            }
        binding.vpNasa.adapter = viewPagerAdapter
        binding.vpNasa.setCurrentItem(viewModel.currentPosition, true)
        binding.vpNasa.offscreenPageLimit = 1
    }
}
