package com.example.androidui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.androidui.util.BottomNavDrawerFragment
import kotlinx.android.synthetic.main.activity_material_components.*

class MaterialComponentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_components)
        material_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                material_button.elevation = progress.toFloat()
                material_button.text = "Rais/Lower Me ${progress.toFloat()}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        // set material BottomAppBar as toolbar
        setSupportActionBar(material_bottomappbar)
        material_bottomappbar.setNavigationOnClickListener {
            Toast.makeText(this, "Navigation menu is clicked", Toast.LENGTH_SHORT).show()
            val bottomNavDrawerFragment = BottomNavDrawerFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }

        // equivalent to onOptionsItemSelectd(item: MenuItem): Boolean
        material_bottomappbar.setOnMenuItemClickListener {
            val menuId = it.itemId
            when (menuId) {
                R.id.search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                R.id.delete -> Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
                R.id.archive -> Toast.makeText(this, "Archive", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }
    // set BottomAppBar menu like usual toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        when (menuId) {
            R.id.search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
            R.id.archive -> Toast.makeText(this, "Archive", Toast.LENGTH_SHORT).show()
        }
        return true
    }*/
}
