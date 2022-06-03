package com.example.myapplication.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LogoutConfirmationFragment(private val onYesLogout: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Вы точно хотите выйти?")
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Да, выйти") { dialog, _ ->
                dialog.dismiss()
                onYesLogout()
            }
            .create()
    }

    companion object {
        val TAG = LogoutConfirmationFragment::class.simpleName
    }
}