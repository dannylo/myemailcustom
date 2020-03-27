package com.example.myemailrecycler.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.NORMAL
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import com.example.myemailrecycler.R
import com.example.myemailrecycler.models.Email
import com.example.myemailrecycler.models.email
import kotlinx.android.synthetic.main.email_item.view.*

class EmailAdapter(val emails: MutableList<Email>) :RecyclerView.Adapter<EmailAdapter.EmailViewHolder>(){

    var selectedItems = SparseBooleanArray()
    private var currentSelectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.email_item, parent, false)
        return EmailViewHolder(view);
    }

    override fun getItemCount(): Int = emails.size;

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        holder.bind(emails[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
        holder.itemView.setOnLongClickListener{
            onItemClickLong?.invoke(position)
            return@setOnLongClickListener true
        }

        if (currentSelectedPosition == position) currentSelectedPosition = -1
    }

    fun toggleSelection(position: Int) {
        currentSelectedPosition = position
        if(selectedItems[position, false]){
            selectedItems.delete(position)
            emails[position].selected = false
        } else {
            selectedItems.put(position, true)
            emails[position].selected = true
        }
        notifyItemChanged(position)
    }

    fun deleteEmails() {
        emails.removeAll(
            emails.filter { it.selected }
        )
        notifyDataSetChanged()
        currentSelectedPosition = -1
    }


    var onItemClick: ((Int) -> Unit)? = null
    var onItemClickLong: ((Int) -> Unit)? = null


    inner class EmailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(email: Email){
            with(email) {
                itemView.email_icon.text = user?.first().toString()
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

                if(email.selected){
                    itemView.email_icon.background = itemView.email_icon.oval(Color.rgb(26, 115, 233))
                    itemView.background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 32f
                        setColor(Color.rgb(232, 240, 253))
                    }
                } else {
                    itemView.background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        cornerRadius = 32f
                        setColor(Color.WHITE)
                    }
                }

                if(selectedItems.isNotEmpty())
                    animate(itemView.email_icon, email)
            }
        }
    }

    private fun animate(view: TextView, email:Email){
        val oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f,0f)
        val oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f,1f)

        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()

        oa1.duration = 200
        oa2.duration = 200
        oa1.addListener(
            object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if(email.selected){
                        view.text = "\u2713"
                    }
                    oa2.start()
                }
            })
        oa1.start()

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