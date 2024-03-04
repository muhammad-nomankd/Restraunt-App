package com.example.resapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuAdapter

class ListMenus : AppCompatActivity() {

    lateinit var mymenus: ListView
    var mylist = arrayListOf<MenuModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_menus)

        var db = MyDb(this, null)

        mymenus = findViewById(R.id.allmenus)

        mylist.addAll(db.allmenus())

        var adapter = MainMenuAdapter(this, mylist)
        mymenus.adapter = adapter

        mymenus.setOnItemClickListener { adapterView, view, i, l ->
            //Toast.makeText(this,"${mylist.get(i).itemname}",Toast.LENGTH_SHORT).show()

            var mid = db.getmid(mylist.get(i).itemname)
            var intent = Intent(this, ListSubMenus::class.java)
            intent.putExtra("id", mid)
            startActivity(intent)
        }

    }
}