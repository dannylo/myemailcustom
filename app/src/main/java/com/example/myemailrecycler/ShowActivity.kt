package com.example.myemailrecycler

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import com.example.myemailrecycler.adapters.oval
import com.example.myemailrecycler.models.Email
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    private var actionMode: ActionMode? = null
    private var position: Int = -1
    private var decisionRemove: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        enableActionMode()


        val email = intent.extras?.getParcelable<Email>("EMAIL")
        position = intent.getIntExtra("position", -1)

        icon_show.text = email?.user?.first().toString()
        subject_show.text = email?.subject
        content_show.text = email?.preview
        date_show.text = email?.date
        name_show.text = email?.user
        if(email!!.stared) img_star.setImageResource(R.drawable.ic_star_black_24dp)
        icon_show.background = icon_show.oval(Color.rgb(email.user.hashCode(), email.user.hashCode()/2, 0))
    }

    private fun enableActionMode(){
        if(actionMode == null){
            actionMode = startSupportActionMode(object: ActionMode.Callback {
                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    when(item?.itemId){
                        R.id.action_delete -> {
                            DeleteConfirmDialog().show(supportFragmentManager, "CONFIRM_DELETE")
                        }
                    }
                    return true
                }

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    finish()
                    mode?.finish()
                }
            })
        }

    }

    fun performEmailRemoving(){
        var intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
