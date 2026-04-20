package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityKickDetailsBinding

class KickDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKickDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityKickDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val kick = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("KICK_DATA", KicksModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("KICK_DATA") as? KicksModel
        }

        if(kick != null){
            binding.detailNameTextView.text = kick.modelName
            binding.detailYearTextView.text = "Rok Produkcji: ${kick.releaseYear}"
            binding.detailBrandTextView.text = "Marka: ${kick.brand}"
            binding.detailPriceTextView.text ="Cena Produktu: ${kick.resellPrice}"


            Glide.with(this)
                .load(kick.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.detailImageId)

            binding.backButton.setOnClickListener {
                finish()
            }
        }else{
            Toast.makeText(
                this,
                "Bład łądowania danych do butów",
                Toast.LENGTH_LONG).show()
            finish()
        }
    }
}