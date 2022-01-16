package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentTopListBinding
import com.example.myapplication.ui.recyclerview.adapters.TopListPagingAdapter
import com.example.myapplication.viewmodel.MainScreenRequest
import com.example.myapplication.viewmodel.TopListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopListFragment : Fragment() {
    private var _binding: FragmentTopListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopListsViewModel by viewModels()

    lateinit var mAdapter: TopListPagingAdapter
    private var requestType: MainScreenRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestType = arguments?.get("request") as MainScreenRequest
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = TopListPagingAdapter()

        binding.movieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getData(requestType ?: MainScreenRequest.TOP_RATED_MOVIES).collectLatest {
                mAdapter.submitData(it)
            }
        }
    }
}