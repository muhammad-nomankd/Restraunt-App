package com.example.resapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.widget.*
import java.io.ByteArrayOutputStream

class SubMenu : AppCompatActivity() {

    lateinit var spinmenus: Spinner
    lateinit var subsize: Spinner

    lateinit var subtitle: EditText
    lateinit var subprice: EditText

    lateinit var subimage: ImageView

    lateinit var subimageget: Button
    lateinit var subadd: Button

    private lateinit var selectedImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_menu)

        //image
        subimage = findViewById(R.id.subimage)

        //spinner
        spinmenus = findViewById(R.id.mainmenu)
        subsize = findViewById(R.id.subsize)

        //EditText
        subtitle = findViewById(R.id.subtitle)
        subprice = findViewById(R.id.subprice)

        //Button
        subimageget = findViewById(R.id.subimageget)
        subadd = findViewById(R.id.subadd)

        var db = MyDb(this, null)

        //spinmenus
        var menulist = arrayListOf<String>()
        menulist.addAll(db.getallmenus())

        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menulist)
        spinmenus.adapter = adapter
        //

        subadd.setOnClickListener {

            var txtname = subtitle.text.toString()
            var txtmenu = spinmenus.selectedItem.toString()
            var mid = db.getmid(txtmenu)
            var txtprice = subprice.text.toString()
            var txtsize = subsize.selectedItem.toString()


            println("SUB Menu Title: $txtname ------------------------------------------------------")
            println("SUB Menu Menu: $txtmenu ------------------------------------------------------")
            println("SUB Menu Price: $txtprice ------------------------------------------------------")
            println("SUB Menu Size: $txtsize ------------------------------------------------------")

            if (!TextUtils.isEmpty(txtname) && !TextUtils.isEmpty(txtprice)) {
                val imgstring = encode(selectedImage)
                db.addsubmenu(txtname, mid, txtprice, txtsize, imgstring)

                Toast.makeText(this, "good", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Registration Fields are Empty", Toast.LENGTH_SHORT).show()
            }
        }

        subimageget.setOnClickListener {
            val galintent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galintent, 111)
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111) {
            selectedImage = data?.data!!
            subimage.setImageURI(selectedImage)

        }
    }

        private fun encode(myimage: Uri): String {
            val input = this.contentResolver.openInputStream(myimage)
            val image = BitmapFactory.decodeStream(input, null, null)

            val mybytes = ByteArrayOutputStream()
            image?.compress(Bitmap.CompressFormat.JPEG, 100, mybytes)
            val imageBytes = mybytes.toByteArray()

            return Base64.encodeToString(imageBytes, Base64.DEFAULT)
        }
}