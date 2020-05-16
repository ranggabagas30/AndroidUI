package com.example.androidui.util


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.androidui.BottomNavigationActivity

import com.example.androidui.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_bottom_nav_drawer.*

/**
 * A simple [Fragment] subclass.
 */
class BottomNavDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bottom_nav_drawer, container, false)
        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener {
            // bottom nav drawer clicks
            when(it.itemId) {
                R.id.nav1 -> {
                    val intent = Intent(activity, BottomNavigationActivity::class.java)
                    activity!!.startActivity(intent)
                }
                R.id.nav2 -> Toast.makeText(context, "Navigation 2", Toast.LENGTH_SHORT).show()
                R.id.nav3 -> Toast.makeText(context, "Navigation 3", Toast.LENGTH_SHORT).show()
            }
            true
        }
        return view
    }


}
