package com.project.colorinterpreter.utils

/**
 * Created by Awodire babajide samuel on 23/05/21.
 * rhymezxcode.github.io/rhymezxcode
 */

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.project.colorinterpreter.R

internal class ProgressLoader(private var context: Context?) {
    private var dialog_main: Dialog? = null

    fun showdialog() {
        dialog_main = context?.let { Dialog(it) }
        dialog_main!!.setContentView(R.layout.loader)
        dialog_main!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog_main!!.setCancelable(true)
        dialog_main!!.show()
    }

    fun hidedialog() {
        dialog_main!!.dismiss()
    }
}