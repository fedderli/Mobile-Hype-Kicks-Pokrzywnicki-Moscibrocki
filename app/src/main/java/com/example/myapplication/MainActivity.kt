package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //seedDataBase()
    }


    private fun seedDataBase(){
        val kicksList =listOf(
            KicksModel(
                brand = "Nike",
                imageUrl = "https://i.postimg.cc/Fz0djtPP/Air-Jordan4.jpg",
                modelName = "Air Jordan 4 Retro Military Blue",
                releaseYear = 2024,
                resellPrice = 1200,
            ),
            KicksModel(
                brand = "New Balance",
                imageUrl = "https://i.postimg.cc/qq2h8fm5/New-Balance-x-MIU-MIU-530-SL-White-1.webp",
                modelName = "530 SL Miu Miu White",
                releaseYear = 2024,
                resellPrice = 9942,
            ),
            KicksModel(
                brand = "adidas",
                imageUrl = "https://i.postimg.cc/MH1Mykst/adidas-Gazelle-Indoor-Bad-Bunny-1.webp",
                modelName = "Gazelle Indoor Core White Black Gum",
                releaseYear = 2024,
                resellPrice = 850,
            ),
            KicksModel(
                brand = "Nike",
                imageUrl = "https://i.postimg.cc/C5bBjyv6/Nike-Dunk-High-Wu-Tang.webp",
                modelName = "Dunk High Wu-Tang (2024 Retro)",
                releaseYear = 2024,
                resellPrice = 2100,
            ),
            KicksModel(
                brand = "Puma",
                imageUrl = "https://i.postimg.cc/66ZvsR9w/Puma-Palmero-Green-White.webp",
                modelName = "Palermo Green White",
                releaseYear = 2024,
                resellPrice = 380,
            ),
            KicksModel(
                brand = "Nike",
                imageUrl = "https://i.postimg.cc/sx5QpFnL/Nike-Moon.jpg",
                modelName = "Moon Shoe Jacquemus Pink",
                releaseYear = 2025,
                resellPrice = 1500,
            ),
            KicksModel(
                brand = "Asics",
                imageUrl = "https://i.postimg.cc/vT5gWRPp/thom-browne-asics-2.jpg",
                modelName = "Gel-Kayano 14 Thom Browne Grey",
                releaseYear = 2026,
                resellPrice = 2800,
            )
        )

        val db = Firebase.firestore

        for (kicks in kicksList) {
            db.collection("HypeKicks")
                .add(kicks)
                .addOnSuccessListener {
                    Log.d("FIREBASE_TEST", "Sukces! Dodano buty: ${kicks.modelName}")
                }
                .addOnFailureListener { e ->
                    Log.e("FIREBASE_TEST", "bląd podczas dodawania", e)
                }

        }
    }
}