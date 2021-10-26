package com.example.myapplication.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class UploadProcessFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setTitle("Пожалуйта, подождите загрузки")
            .setView(R.layout.fragment_upload_process)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(false)
    }

    companion object {
        val TAG = UploadProcessFragment::class.simpleName
    }
}