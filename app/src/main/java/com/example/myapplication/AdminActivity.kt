package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAdminBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAdminBinding
    private val db = FirebaseFirestore.getInstance()


    private lateinit var kicksList: MutableList<KicksModel>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var displayNames: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kicksList = mutableListOf()
        displayNames = mutableListOf()


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNames)
        binding.lvAdminSneakers.adapter = adapter


        loadKicksForAdmin()


        binding.btnAddSneaker.setOnClickListener {
            addNewKick()
        }


        binding.lvAdminSneakers.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteDialog(position)
            true
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadKicksForAdmin() {

        db.collection("HypeKicks").addSnapshotListener { snapshots, e ->
            if (e != null) return@addSnapshotListener

            if (snapshots != null) {
                kicksList.clear()
                displayNames.clear()
                for (doc in snapshots) {
                    val kick = doc.toObject(KicksModel::class.java)

                    kick.id = doc.id

                    kicksList.add(kick)
                    displayNames.add("${kick.brand} ${kick.modelName} - ${kick.resellPrice} PLN")
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun addNewKick() {
        val brand = binding.etBrand.text.toString()
        val model = binding.etModel.text.toString()
        val year = binding.etYear.text.toString().toIntOrNull() ?: 0
        val price = binding.etPrice.text.toString().toIntOrNull() ?: 0
        val url = binding.etImageUrl.text.toString()

        if (brand.isNotEmpty() && model.isNotEmpty()) {
            val kickData = hashMapOf(
                "brand" to brand,
                "imageUrl" to url,
                "modelName" to model,
                "releaseYear" to year,
                "resellPrice" to price
            )

            db.collection("HypeKicks")
                .add(kickData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Dodano buta do bazy!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Błąd: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Wypełnij markę i model!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteDialog(position: Int) {
        val kick = kicksList[position]
        AlertDialog.Builder(this)
            .setTitle("Usuwanie")
            .setMessage("Czy chcesz usunąć ${kick.modelName}?")
            .setPositiveButton("Tak") { _, _ ->
                db.collection("HypeKicks").document(kick.id).delete()
            }
            .setNegativeButton("Nie", null)
            .show()
    }

    private fun clearFields() {
        binding.apply {
            etBrand.text.clear()
            etModel.text.clear()
            etYear.text.clear()
            etPrice.text.clear()
            etImageUrl.text.clear()
        }
    }
}