package com.example.resapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class SubMenuAdapter(var context: Context, var mylist: ArrayList<SubmenuModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return mylist.size
    }

    override fun getItem(p0: Int): Any {
        return mylist.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.subview, p2, false)

        var SubName: TextView = view.findViewById(R.id.sName)
        var SubSize: TextView = view.findViewById(R.id.sSize)
        var SubPrice: TextView = view.findViewById(R.id.sPrice)

        var SubImage: ImageView = view.findViewById(R.id.sImage)

        var nametext = mylist.get(p0).subname
        var pricetext = mylist.get(p0).subprice
        var sizetext = mylist.get(p0).subsize

        var picimage = mylist.get(p0).subimage

        var imgbitmap = decode(picimage)

        SubName.text = nametext
        SubPrice.text = pricetext.toString()
        SubSize.text = sizetext

        SubImage.setImageBitmap(imgbitmap)

        return view
    }

    private fun decode(imageString: String): Bitmap? {
        var imageBytes = Base64.decode(imageString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}
