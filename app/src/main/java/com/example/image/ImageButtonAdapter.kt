package com.example.image

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageButtonAdapter(
    private val imageButtonList: ArrayList<Image>,
    private val context : Context
):RecyclerView.Adapter<ImageButtonAdapter.ButtonViewHolder>() {

    class ButtonViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageButton : ImageButton = itemView.findViewById(R.id.imageButton)
        val category : TextView = itemView.findViewById(R.id.category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_button_item,parent, false)
        return ButtonViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imageButtonList.size
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.category.text = imageButtonList[position].imageName
        Glide.with(context).load(imageButtonList[position].imageUrl).into(holder.imageButton)

        holder.imageButton.setOnClickListener {

            val scaleAnimation = ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            scaleAnimation.duration = 300
            holder.imageButton.startAnimation(scaleAnimation)

            var intent = Intent(context, ImagesScreen::class.java)
            intent.putExtra("category",imageButtonList[position].imageName)
            context.startActivity(intent)
        }

    }
}