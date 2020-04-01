package com.example.myemailrecycler

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class DeleteConfirmDialog :DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var showActivity = activity as ShowActivity
        return showActivity?.let {
            val builderDialog = AlertDialog.Builder(it)
            builderDialog
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{
                    dialog, id ->
                    Log.i("DIALOG", "TODO - REMOVE THIS EMAIL.")
                        showActivity.performEmailRemoving()
                        dialog.cancel()

                })
                .setNegativeButton("Not", DialogInterface.OnClickListener{
                    dialog, id ->  dialog.cancel()
                })

            builderDialog.create()
        }?: throw  IllegalStateException("The activity cannot be null.")
    }
}