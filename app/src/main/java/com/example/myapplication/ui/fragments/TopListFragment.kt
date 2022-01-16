package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentTopListBinding
import com.example.myapplication.viewmodel.ListsViewModel
import com.example.myapplication.viewmodel.TopListsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopListFragment : Fragment() {
    private var _binding: FragmentTopListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopListBinding.inflate(inflater, container, false)
        return binding.root
    }
}