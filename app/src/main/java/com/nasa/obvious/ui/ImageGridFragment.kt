package com.nasa.obvious.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nasa.obvious.R
import com.nasa.obvious.databinding.FragmentImageListBinding
import com.nasa.obvious.ui.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageGridFragment : Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<ImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ImageGridFragment.viewLifecycleOwner
            viewModel = this@ImageGridFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageAdapter = ImageAdapter {
            viewModel.currentPosition = it
            findNavController().navigate(R.id.action_ImageGridFragment_to_ImageViewPagerFragment)
        }
        binding.rvNasa.adapter = imageAdapter
        loadNasaImages()
        viewModel.images.observe(viewLifecycleOwner) {
            if (it?.first != null) {
                imageAdapter.submitList(it.first)
            }
        }
        binding.btnRetry.setOnClickListener { loadNasaImages() }
    }

    private fun loadNasaImages() {
        binding.llNoImageOrFailed.visibility = View.GONE
        viewModel.fetchNasaListImage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}