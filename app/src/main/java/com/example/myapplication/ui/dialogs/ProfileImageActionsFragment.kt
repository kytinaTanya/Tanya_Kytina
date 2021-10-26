package com.example.myapplication.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myapplication.ui.fragments.AccountFragment

class ProfileImageActionsFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setItems(
                arrayOf("Открыть фотографию", "Изменить фотографию", "Удалить фотографию")
            ) { dialog, which ->
                when(which) {
                    0 -> {
                        dialog.dismiss()
                        (parentFragment as AccountFragment).openPhoto()
                        //Toast.makeText(requireContext(), "Открыть фото", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        dialog.dismiss()
                        (parentFragment as AccountFragment).openGalleryForImage()
                        //Toast.makeText(requireContext(), "Изменить фото", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        //Toast.makeText(requireContext(), "Удалить фото", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        (parentFragment as AccountFragment).checkDecision()
                    }
                }

            }
            .create()
    }
    companion object {
        val TAG = ProfileImageActionsFragment::class.simpleName
    }
}