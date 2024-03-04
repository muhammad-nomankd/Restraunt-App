package com.example.resapp


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

class MainMenuAdapter(var context: Context, var mylist: ArrayList<MenuModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return mylist.size
    }

    override fun getItem(p0: Int): Any {
        return mylist.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.mainadapter, p2, false)

        //mainadapter
        var nameview = view.findViewById<TextView>(R.id.pName)
        var imgview = view.findViewById<ImageView>(R.id.pImage)

        //MenuModel
        var txtname = mylist.get(p0).itemname
        var txtimage = mylist.get(p0).uItemimage

        var bitmapImage = decode(txtimage)

        //mainadapter & MainMenu
        nameview.text = txtname
        imgview.setImageBitmap(bitmapImage)

        return view
    }

    private fun decode(imageString: String): Bitmap? {
        var imageBytes = Base64.decode(imageString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }


}