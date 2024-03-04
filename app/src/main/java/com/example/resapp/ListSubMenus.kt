package com.example.resapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class ListSubMenus : AppCompatActivity() {

    lateinit var mymenus: ListView
    var mylist = arrayListOf<SubmenuModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sub_menus)

        var db = MyDb(this, null)

        var mid = intent.getIntExtra("id", 555)

        mymenus = findViewById(R.id.allsubmenus)

        //mylist.addAll(db.allsubmenus())
        mylist.addAll(db.requiresubmenus(mid))

        var adapter = SubMenuAdapter(this, mylist)
        mymenus.adapter = adapter

        mymenus.setOnItemClickListener { adapterView, view, i, l ->

            var sid = db.getsid(mylist.get(i).subname)
            //println("Sub menu ID---------------------------------------------$sid")

            var intent = Intent(this,AddItem::class.java)

            intent.putExtra("sid",sid)
            startActivity(intent)
        }
    }
}