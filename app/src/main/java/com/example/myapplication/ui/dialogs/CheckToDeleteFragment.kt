package com.example.myapplication.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myapplication.ui.fragments.AccountFragment

class CheckToDeleteFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Удалить фото")
            .setMessage("Вы точно хотите удалить фотографию?")
            .setPositiveButton("Удалить") { dialog, which ->
                (parentFragment as AccountFragment).deletePhoto()
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, which ->
                dialog.cancel()
            }
            .create()
    }

    companion object {
        val TAG = CheckToDeleteFragment::class.simpleName
    }
}
