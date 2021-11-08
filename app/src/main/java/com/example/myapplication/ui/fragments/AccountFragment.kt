package com.example.myapplication.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.firebase.AUTH
import com.example.myapplication.firebase.USER
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.ui.activities.SingInActivity
import com.example.myapplication.ui.dialogs.CheckToDeleteFragment
import com.example.myapplication.ui.dialogs.ProfileImageActionsFragment
import com.example.myapplication.ui.dialogs.UploadProcessFragment
import com.example.myapplication.utils.setImage
import com.example.myapplication.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AccountViewModel>()
    private var inputStream: InputStream? = null
    private var dialog: UploadProcessFragment = UploadProcessFragment()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        binding.profileImage.setImageURI(uri)

        if(uri != null) {
            inputStream = activity?.contentResolver?.openInputStream(uri)!!
        }
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
        val image = USER.profileUrl
        Log.d("USER", "${USER}")
        if(image != "") {
            setImage(USER.profileUrl)
        }
        binding.exitButton.setOnClickListener {
            AUTH.signOut()
            val intent = Intent(activity, SingInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.profileImage.setOnClickListener {
            ProfileImageActionsFragment().show(
                childFragmentManager, ProfileImageActionsFragment.TAG
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if(inputStream != null) {
            viewModel.uploadImage(inputStream!!)
            dialog.show(
                childFragmentManager, UploadProcessFragment.TAG
            )
            inputStream = null
        }

        viewModel.profileImageUrl.observe(this) { url ->
            MainActivity.setProfileImage(url)
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openGalleryForImage() {
        getContent.launch("image/*")
    }

    fun openPhoto() {
        val action = AccountFragmentDirections.actionAccountPageToPhotoFragment(USER.profileUrl)
        view?.findNavController()?.navigate(action)
    }

    fun checkDecision() {
        CheckToDeleteFragment().show(
            childFragmentManager, CheckToDeleteFragment.TAG
        )
    }

    fun deletePhoto() {
        MainActivity.setProfileImage("")
        setImage("")
    }

    private fun setImage(url: String) {
        binding.profileImage.setImage(url)
    }
}