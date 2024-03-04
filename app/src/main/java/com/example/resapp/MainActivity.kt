package com.example.resapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var mainmenu: Button
    lateinit var mainview: Button
    lateinit var submenu: Button
    lateinit var subview: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainmenu = findViewById(R.id.mainmenu)
        mainview = findViewById(R.id.mainview)
        submenu = findViewById(R.id.sub)
        /*subview=findViewById(R.id.subview)*/

        mainmenu.setOnClickListener {
            startActivity(Intent(this, MenuMain::class.java))
        }

        mainview.setOnClickListener {
            startActivity(Intent(this, ListMenus::class.java))
        }

        submenu.setOnClickListener {
            startActivity(Intent(this, SubMenu::class.java))
        }

        /*subview.setOnClickListener {
            startActivity(Intent(this,ListSubMenus::class.java))
        }*/


    }
}
