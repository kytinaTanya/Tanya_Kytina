package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentExampleBinding

class ExampleFragment : Fragment(){
    private var _binding: FragmentExampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.tvFragment.setOnClickListener {
            val i = Intent(context, SecondActivity::class.java)
            startActivity(i)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}