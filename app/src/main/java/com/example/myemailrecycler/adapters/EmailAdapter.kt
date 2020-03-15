package com.example.myemailrecycler.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.myemailrecycler.R
import com.example.myemailrecycler.models.Email
import kotlinx.android.synthetic.main.email_item.view.*

class EmailAdapter(val emails: MutableList<Email>) :RecyclerView.Adapter<EmailAdapter.EmailViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.email_item, parent, false)
        return EmailViewHolder(view);
    }

    override fun getItemCount(): Int = emails.size;

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
    }

    fun  

    inner class EmailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(email: Email){
            with(email) {
                itemView.email_icon.text = user.first().toString()
                itemView.email_icon.background = itemView.oval(Color.rgb(user.hashCode(), user.hashCode()/2, 0))

                itemView.user.text = user
                itemView.subject.text = subject
                itemView.preview.text = preview
                itemView.date.text = date

                itemView.user.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                itemView.subject.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)
                itemView.date.setTypeface(Typeface.DEFAULT, if (unread) BOLD else NORMAL)

                itemView.img_star.setImageResource(if (stared) R.drawable.ic_star_black_24dp else
                                R.drawable.ic_star_border_black_24dp)
            }
        }
    }
}

fun View.oval(@ColorInt color: Int): ShapeDrawable {
    val oval = ShapeDrawable(OvalShape())
    with(oval){
        intrinsicHeight = height
        intrinsicWidth = width
        paint.color = color
    }
    return oval
}