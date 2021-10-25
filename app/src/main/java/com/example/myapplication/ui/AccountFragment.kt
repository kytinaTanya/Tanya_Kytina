package com.example.myapplication.ui

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.ui.MainActivity.Companion.USER
import com.example.myapplication.viewmodel.AccountViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.InputStream
import java.util.*

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AccountViewModel>()
    private var file: ByteArray? = null
    private var inputStream: InputStream? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.profileImage.setImageURI(uri)

        if(uri != null) {
            inputStream = requireContext().contentResolver.openInputStream(uri)!!
        }

        //file = uri?.path?.readBytes()
        //viewModel.uploadImage(File(uri as URI).readBytes())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.username.text = USER.username
        binding.email.text = USER.email
        binding.exitButton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity, SingInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.profileImage.setOnClickListener {
            openGalleryForImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if(inputStream != null) {
            viewModel.uploadImage(inputStream!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openGalleryForImage() {
            getContent.launch("image/*")
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}