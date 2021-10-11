package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHistoryBinding
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.ui.recyclerview.DividerItemDecoration
import com.example.myapplication.ui.recyclerview.adapters.HistoryRecyclerAdapter
import com.example.myapplication.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryRecyclerAdapter
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("USER LIST ID", "${USER.historyListID} AND SESSION ID ${USER.sessionKey}")
        viewModel.loadHistory(USER.historyListID, USER.sessionKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.films.observe(this) { films ->
            historyAdapter.appendFilms(films)
        }
    }

    private fun initRecyclerView() {
        historyAdapter = HistoryRecyclerAdapter()
        binding.historyListFilms.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(DividerItemDecoration(32))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}