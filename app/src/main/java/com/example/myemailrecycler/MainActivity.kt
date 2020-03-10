package com.example.myemailrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myemailrecycler.adapters.EmailAdapter
import com.example.myemailrecycler.models.EmailBuilder
import com.example.myemailrecycler.models.generateFakeEmails
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view_main.adapter = EmailAdapter(generateFakeEmails())
        recycler_view_main.layoutManager = LinearLayoutManager(this);
    }
}
