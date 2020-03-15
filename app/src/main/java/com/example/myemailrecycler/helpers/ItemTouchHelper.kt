package com.example.myemailrecycler.helpers

import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myemailrecycler.adapters.EmailAdapter
import java.util.*


class ItemTouchHelper(var dragDirs: Int, var swipeDirs: Int, var adapter: EmailAdapter): ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.emails.removeAt(viewHolder.adapterPosition)
        adapter.notifyItemRemoved(viewHolder.adapterPosition)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        val from = viewHolder.adapterPosition
        val to = target.adapterPosition

        adapter.notifyItemMoved(from, to)
        Collections.swap(adapter.emails, from, to)

        return true
    }
}