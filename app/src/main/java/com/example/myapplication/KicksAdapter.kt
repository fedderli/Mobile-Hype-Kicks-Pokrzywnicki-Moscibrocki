package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemKicksBinding

class KicksAdapter(
    private val context: Context,
    private val kicksList: List<KicksModel>
) : BaseAdapter() {

    override fun getCount(): Int = kicksList.size

    override fun getItem(position: Int): Any = kicksList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ItemKicksBinding
        val view: View

        if (convertView == null){
            val inflater = LayoutInflater.from(context)
            binding = ItemKicksBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        }else{
            view = convertView
            binding = view.tag as ItemKicksBinding
        }

        val kick = kicksList[position]

        binding.kicksFullName.text = kick.modelName
        binding.kicksPrice.text = "cena: ${kick.resellPrice}"

        Glide.with(context)
            .load(kick.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.kicksImageView )

        return view
    }



}