package com.example.myemailrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myemailrecycler.adapters.EmailAdapter
import com.example.myemailrecycler.models.EmailBuilder
import com.example.myemailrecycler.models.email
import com.example.myemailrecycler.models.generateFakeEmails
import com.mooveit.library.Fakeit
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EmailAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fakeit.init()
        adapter = EmailAdapter(generateFakeEmails())
        recycler_view_main.adapter = adapter
        recycler_view_main.layoutManager = LinearLayoutManager(this);

        fab.setOnClickListener{
            addEmail()
            recycler_view_main.scrollToPosition(0)
            Toast.makeText(this, "Random e-mail succefully genereted.", Toast.LENGTH_SHORT).show()
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
