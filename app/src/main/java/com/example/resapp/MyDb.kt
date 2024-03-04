package com.example.resapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDb(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, dbname, factory, 1) {

    companion object {

        var dbname = "Record"
        var tblid = "itemid"

        //table names
        var tblmenu = "menu"
        var tblsubmenu = "submenu"
        var tblcart = "cart"

        //cart table
        var tblcartsid = "subid"
        var tblcartqty = "qty"
        var tblcarttotal = "total"

        //main menu
        var tblitemname = "itemaname"
        var tblitemimage = "itemstring"

        //submenu
        var tblsubname = "subname"
        var tblsubprice = "subprice"
        var tblsubsize = "subsize"
        var tblsubimage = "substring"
        var tblsubmid = "submid"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query_menu =
            "create table $tblmenu ($tblid INTEGER PRIMARY KEY AUTOINCREMENT, $tblitemname TEXT, $tblitemimage TEXT)"
        db?.execSQL(query_menu)

        val query_submenu =
            "create table $tblsubmenu ($tblid INTEGER PRIMARY KEY AUTOINCREMENT, $tblsubname TEXT, $tblsubprice TEXT, $tblsubsize TEXT, $tblsubimage TEXT, $tblsubmid INTEGER)"
        db?.execSQL(query_submenu)

        val query_cart =
            "create table $tblcart ($tblid INTEGER PRIMARY KEY AUTOINCREMENT, $tblcartsid INTEGER, $tblcartqty INTEGER, $tblcarttotal INTEGER"
        db?.execSQL(query_cart)


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tblmenu")
        db?.execSQL("DROP TABLE IF EXISTS $tblsubmenu")
        db?.execSQL("DROP TABLE IF EXISTS $tblcart")
        onCreate(db)
    }

    fun addmenu(name: String, img: String) {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(tblitemname, name)
        value.put(tblitemimage, img)

        db.insert(tblmenu, null, value)
        db.close()
    }

    //db.addsubmenu(txtname,txtmenu,txtprice,txtsize,imgstring)
    fun addsubmenu(subname: String, mid: Int, subprice: String, subsize: String, subimg: String) {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(tblsubname, subname)
        value.put(tblsubprice, subprice)
        value.put(tblsubsize, subsize)
        value.put(tblsubimage, subimg)
        value.put(tblsubmid, mid)

        db.insert(tblsubmenu, null, value)
        db.close()
    }


    fun getallmenus(): ArrayList<String> {
        val menus = arrayListOf<String>()

        val db = this.readableDatabase
        val allmenus = "select * from $tblmenu"
        val cursor = db.rawQuery(allmenus, null)

        if (cursor.moveToFirst()) {
            do {
                val menuname = cursor.getString(1).toString()
//                var subprice = cursor.getString(2).toString()
//                var subsize = cursor.getString(3).toString()
//                var subimage = cursor.getString(4).toString()

                menus.add(menuname)
            } while (cursor.moveToNext())

        }

        return menus
    }


    fun allmenus(): ArrayList<MenuModel> {

        val mylist = arrayListOf<MenuModel>()
        val db = this.readableDatabase
        val allmenus = "select * from $tblmenu"
        val cursor = db.rawQuery(allmenus, null)

        if (cursor.moveToFirst()) {
            do {
                val menuname = cursor.getString(1).toString()
                val menuimage = cursor.getString(2).toString()

                mylist.add(MenuModel(menuname, menuimage))
            } while (cursor.moveToNext())

        }
        return mylist
    }

    //NOT USED
    /* fun allsubmenus(): ArrayList<SubmenuModel> {

         var mylist = arrayListOf<SubmenuModel>()
         var db = this.readableDatabase
         var allmenus = "select * from $tblsubmenu"
         var cursor = db.rawQuery(allmenus, null)

         if (cursor.moveToFirst()) {
             do {
                 var submenuname = cursor.getString(1).toString()
                 var submenuprice = cursor.getString(2).toString().toInt()
                 var submenusize = cursor.getString(3).toString()
                 var submenuimage = cursor.getString(4).toString()
                 var mainid = cursor.getInt(5)

                 mylist.add(
                     SubmenuModel(
                         submenuname, submenuprice, submenusize, submenuimage, mainid
                     )
                 )
             } while (cursor.moveToNext())

         }
         return mylist
     }*/

    fun getmid(menuname: String): Int {
        var mid = 0

        val db = this.readableDatabase

        val query = "select * from $tblmenu where $tblitemname='$menuname'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                mid = cursor.getInt(0)
            } while (cursor.moveToNext())
        }

        return mid
    }

    fun getsid(subname: String): Int {
        var sid = 0

        val db = this.readableDatabase

        val query = "select * from $tblsubmenu where $tblsubname='$subname'"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                sid = cursor.getInt(0)
            } while (cursor.moveToNext())
        }

        return sid
    }

    fun requiresubmenus(mid: Int): ArrayList<SubmenuModel> {
        val mylist = arrayListOf<SubmenuModel>()
        val db = this.readableDatabase
        val allmenus = "select * from $tblsubmenu where $tblsubmid='$mid'"
        val cursor = db.rawQuery(allmenus, null)

        if (cursor.moveToFirst()) {
            do {
                val submenuname = cursor.getString(1).toString()
                val submenuprice = cursor.getString(2).toString().toInt()
                val submenusize = cursor.getString(3).toString()
                val submenuimage = cursor.getString(4).toString()
                val mainid = cursor.getInt(5)

                mylist.add(
                    SubmenuModel(
                        submenuname, submenuprice, submenusize, submenuimage, mainid
                    )
                )
            } while (cursor.moveToNext())

        }
        return mylist
    }

    fun getsubrow(sid:Int):ArrayList<SubmenuModel>
    {
        var mylist = arrayListOf<SubmenuModel>()

        var db = this.readableDatabase
        var query = "select * from $tblsubmenu where $tblid"

        var cursor = db.rawQuery(query,null)
        if (cursor.moveToFirst()) {
            var sname = cursor.getString(1)
            var sprice = cursor.getString(2).toInt()
            var ssize = cursor.getString(3)
            var simage = cursor.getString(4)
            var smid = cursor.getString(5).toInt()

            mylist.add(SubmenuModel(sname, sprice, ssize, simage, smid))
        }
        return mylist
    }

    fun addcartitem(sid:Int,total:Int,qty:Int)
    {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(tblcartsid, sid)
        value.put(tblcartqty, qty)
        value.put(tblcarttotal, total)


        db.insert(tblsubmenu, null, value)
        db.close()
    }

}

