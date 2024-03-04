package com.example.resapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class AddItem : AppCompatActivity() {

    lateinit var txtname: TextView
    lateinit var txtprice: TextView
    lateinit var txtqty: EditText

    lateinit var imgitem: ImageView

    lateinit var btndone: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        var db = MyDb(this,null)

        var sid = intent.getIntExtra("sid",420)
        var mylist = arrayListOf<SubmenuModel>()
        mylist.addAll(db.getsubrow(sid))

        txtname = findViewById(R.id.itemname)
        txtprice = findViewById(R.id.itemprice)
        txtqty = findViewById(R.id.itemqty)
        imgitem = findViewById(R.id.itemimage)
        btndone = findViewById(R.id.itemsubmit)

        txtname.text = mylist.get(0).subname
        txtprice.text = mylist.get(0).subprice.toString()

        var bitmap = decode(mylist.get(0).subimage)
        imgitem.setImageBitmap(bitmap)


        btndone.setOnClickListener {
            var itemprice = txtprice.text.toString().toInt()
            var itemqty = txtqty.text.toString().toInt()
            var total = (itemprice * itemqty)
            db.addcartitem(sid,total,itemqty)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    private fun decode(imageString: String): Bitmap? {
        var imageBytes = Base64.decode(imageString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}