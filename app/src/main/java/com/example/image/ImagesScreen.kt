package com.example.image

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage

class ImagesScreen : AppCompatActivity() {

    private lateinit var feed : RecyclerView
    private lateinit var imagesList : ArrayList<Image>

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_screen)

        feed = findViewById(R.id.feed)
        feed.layoutManager = LinearLayoutManager(this)

        imagesList = arrayListOf()

        var imageAdapter = ImageAdapter(imagesList, this@ImagesScreen)
        feed.adapter = imageAdapter

        var category = intent.getStringExtra("category").toString()

        val storageReference = FirebaseStorage.getInstance().reference.child("images").child(category)
        storageReference.listAll()
            .addOnSuccessListener { listResult ->
                for (item in listResult.items) {
                    item.downloadUrl
                        .addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            val imageName = item.name.substringBeforeLast(".")
                            imagesList.add(Image(imageUrl, imageName))
                            imageAdapter.notifyDataSetChanged()
                        }
                        .addOnFailureListener {
                        }
                }
            }
            .addOnFailureListener {
            }

    }
}