package com.example.image

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(
    private val imagesList:ArrayList<Image>,
    private val context: Context
) :RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.image)
        val saveButton : Button = itemView.findViewById(R.id.saveButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false)
        return ImageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(imagesList[position].imageUrl).into(holder.image)

        holder.saveButton.setOnClickListener{
            val imageUri = imagesList[position].imageUrl
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(imageUri))
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "image.jpg")
            downloadManager.enqueue(request)
        }

    }
}