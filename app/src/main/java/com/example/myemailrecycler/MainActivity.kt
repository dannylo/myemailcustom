package com.example.myemailrecycler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.core.util.isEmpty
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myemailrecycler.adapters.EmailAdapter
import com.example.myemailrecycler.models.EmailBuilder
import com.example.myemailrecycler.models.email
import com.example.myemailrecycler.models.generateFakeEmails
import com.mooveit.library.Fakeit
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EmailAdapter;
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fakeit.init()
        adapter = EmailAdapter(generateFakeEmails())
        recycler_view_main.adapter = adapter
        recycler_view_main.layoutManager = LinearLayoutManager(this);

        adapter.onItemClick = {
            if (adapter.selectedItems.isEmpty()){
                var intent = Intent(this, ShowActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("EMAIL", adapter.emails.get(it))
                intent = intent.putExtras(bundle)
                startActivity(intent)
            } else {
                enableActionMode(it)
            }
        }

        adapter.onItemClickLong = {
            enableActionMode(it)

        }

        fab.setOnClickListener{
            addEmail()
            recycler_view_main.scrollToPosition(0)
            Toast.makeText(this, "Random e-mail succefully genereted.", Toast.LENGTH_SHORT).show()
        }

        val helper = ItemTouchHelper(
            com.example.myemailrecycler.helpers.ItemTouchHelper(
                0, ItemTouchHelper.LEFT, adapter)
            )

        helper.attachToRecyclerView(recycler_view_main)
    }

    private fun enableActionMode(position: Int){
        if(actionMode == null){
            actionMode = startSupportActionMode(object: ActionMode.Callback {
                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    if(item?.itemId == R.id.action_delete){
                        adapter.deleteEmails()
                        mode?.finish()
                        return true
                    }
                    return false
                }

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode?.menuInflater?.inflate(R.menu.menu_delete, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    adapter.selectedItems.clear()
                    adapter.emails
                        .filter { it.selected }
                        .forEach{ it.selected = false}
                    adapter.notifyDataSetChanged()
                    actionMode = null
                }
            })
        }
        adapter.toggleSelection(position)
        val size = adapter.selectedItems.size()
        if(size == 0){
            actionMode?.finish()
        } else {
            actionMode?.title = "$size"
            actionMode?.invalidate()
        }
    }
    fun addEmail(){
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).parse(
            Fakeit.dateTime().dateFormatter()
        )
        adapter.emails.add(0, email {
            stared = false
            unread = true
            user = Fakeit.name().firstName();
            subject = Fakeit.company().name();
            date = SimpleDateFormat("d MMM", Locale("pt", "BR")).format(sdf)
            preview = mutableListOf<String>().apply {
                repeat(10){
                    add(Fakeit.lorem().words())
                }
            }.joinToString(" ")
        })

        adapter.notifyItemInserted(0)
    }
}
