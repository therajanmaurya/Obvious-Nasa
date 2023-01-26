package com.nasa.obvious.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nasa.obvious.databinding.FragmentImageListBinding
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
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchNasaListImage()
        val imageAdapter = ImageAdapter {

        }
        binding.rvNasa.adapter = imageAdapter
        viewModel.images.observe(viewLifecycleOwner) {
            it?.let { imageAdapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}