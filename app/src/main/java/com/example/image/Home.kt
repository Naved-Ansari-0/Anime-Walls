package com.example.image

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.security.Signature
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
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

}

