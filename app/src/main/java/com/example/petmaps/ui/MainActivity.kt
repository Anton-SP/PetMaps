package com.example.petmaps.ui

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petmaps.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mark_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_mark_list -> Toast.makeText(this,"GOTO LIST MARKERS",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}