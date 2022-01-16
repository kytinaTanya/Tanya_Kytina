package com.example.myapplication.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myapplication.BuildConfig
import com.example.myapplication.firebase.USER
import com.example.myapplication.ui.fragments.AccountFragment

class ProfileImageActionsFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = if (USER.profileUrl == BuildConfig.DEFAULT_ACCOUNT_IMAGE_URL) arrayOf(
            "Изменить фотографию",
            "Удалить фотографию"
        ) else arrayOf("Изменить фотографию", "Удалить фотографию", "Открыть фотографию")
        return AlertDialog.Builder(requireContext())
            .setItems(
                items
            ) { dialog, which ->
                when (which) {
                    0 -> {
                        dialog.dismiss()
                        (parentFragment as AccountFragment).openGalleryForImage()
                    }
                    1 -> {
                        dialog.dismiss()
                        (parentFragment as AccountFragment).checkDecision()
                    }
                    2 -> {
                        dialog.dismiss()
                        (parentFragment as AccountFragment).openPhoto()
                    }
                }

            }
            .create()
    }
    companion object {
        val TAG = ProfileImageActionsFragment::class.simpleName
    }
}