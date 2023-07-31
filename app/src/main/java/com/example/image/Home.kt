package com.example.image

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import kotlin.collections.ArrayList

class Home : AppCompatActivity() {

    private lateinit var categories : RecyclerView
    private lateinit var imageButtonList : ArrayList<Image>

    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId", "PackageManagerGetSignatures")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        categories = findViewById(R.id.categories)
        categories.layoutManager = GridLayoutManager(this,2)

        imageButtonList = arrayListOf()

        var imageButtonAdapter = ImageButtonAdapter(imageButtonList, this@Home)
        categories.adapter = imageButtonAdapter

        val storageReference = FirebaseStorage.getInstance().reference.child("category")
        storageReference.listAll()
            .addOnSuccessListener { listResult ->
                for (item in listResult.items) {
                    item.downloadUrl
                        .addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            val imageName = item.name.substringBeforeLast(".")
                            imageButtonList.add(Image(imageUrl, imageName))
                            imageButtonAdapter.notifyDataSetChanged()
                        }
                        .addOnFailureListener {
                        }
                }
            }
            .addOnFailureListener {

            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.privacyPolicy -> {
                val url = "https://docs.google.com/document/d/1YVIgGpKQmOM18ey4oInEe8bjXF7QyBg8BjQDRt3lagY/edit?usp=sharing"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            R.id.about -> {
                startActivity(Intent(this, About::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

