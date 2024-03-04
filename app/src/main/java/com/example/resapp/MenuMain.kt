package com.example.resapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream

class MenuMain : AppCompatActivity() {

    private lateinit var pizzaname: EditText

    private lateinit var imagebtn: Button
    private lateinit var registerbtn: Button

    private lateinit var pizzaimage: ImageView

    private lateinit var selectedImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_main)

        val db = MyDb(this, null)

        pizzaname = findViewById(R.id.pizzaname)

        imagebtn = findViewById(R.id.imagebtn)
        registerbtn = findViewById(R.id.registerbtn)

        pizzaimage = findViewById(R.id.pizzaimage)



        registerbtn.setOnClickListener {

            val txtpname = pizzaname.text.toString()

            if (!TextUtils.isEmpty(txtpname)) {
                val imgstring = encode(selectedImage)
                db.addmenu(txtpname, imgstring)
                //db.addusers(txtpname,txtpprice,txtpsize,imgstring)

                Toast.makeText(this, "good", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Registration Fields are Empty", Toast.LENGTH_SHORT).show()
            }
        }

        imagebtn.setOnClickListener {
            val galintent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galintent, 555)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 555) {

            selectedImage = data?.data!!
            pizzaimage.setImageURI(selectedImage)

        }
    }

    private fun encode(myimage: Uri): String {
        val input = this.contentResolver.openInputStream(myimage)
        val image = BitmapFactory.decodeStream(input, null, null)

        val mybytes = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, mybytes)
        val imageBytes = mybytes.toByteArray()
        /*val imageString = Base64.encodeToString(imageBytes,Base64.DEFAULT)*/

        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
}