package com.example.androidui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.example.androidui.util.UI
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    lateinit var badgeDrawable: BadgeDrawable
    lateinit var badge: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        material_bottomnav.setOnNavigationItemSelectedListener {
            val menuItemId = it.itemId
            when(menuItemId) {
                R.id.home -> {
                }
            }
            true
        }

        //addBadge(1)
        //addBadge2(R.id.nearme)
        addBadge3(R.id.home, 9)
    }

    private fun addBadge(position: Int) {
        val bottomNavigationMenu = material_bottomnav.getChildAt(0) as? BottomNavigationMenuView
        val bottomSelectedMenuItem = bottomNavigationMenu?.getChildAt(position) as? BottomNavigationItemView

        badge = LayoutInflater.from(this).inflate(R.layout.badge_layout, bottomNavigationMenu, false)
        /*badge.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            Log.d("badge", "(left, top, right, bottom) : ($left, $top, $right, $bottom)")
            Log.d("badge", "badge height: ${v.height} px -> ${UI.pxToDp(this@BottomNavigationActivity, v.height.toFloat())} dp")
            Log.d("badge", "badge width: ${v.width} px -> ${UI.pxToDp(this@BottomNavigationActivity, v.width.toFloat())} dp")

            val badgeLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(badge.layoutParams).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                leftMargin = UI.pxToDp(this@BottomNavigationActivity, badge.width / 2f).toInt()
            }

            bottomSelectedMenuItem?.addView(badge, badgeLayoutParams)
        }*/
        badge.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (badge.measuredWidth > 0 && badge.measuredHeight > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        badge.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    } else {
                        badge.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                    Log.d("badge", "(left, top, right, bottom) : (${badge.left}, ${badge.top}, ${badge.right}, ${badge.bottom})")
                    Log.d("badge", "badge height: ${badge.height} px -> ${UI.pxToDp(this@BottomNavigationActivity, badge.height.toFloat())} dp")
                    Log.d("badge", "badge width: ${badge.width} px -> ${UI.pxToDp(this@BottomNavigationActivity, badge.width.toFloat())} dp")

                    val badgeLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(badge.layoutParams).apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                        leftMargin = UI.pxToDp(this@BottomNavigationActivity, badge.width / 2f).toInt()
                    }

                    bottomSelectedMenuItem?.addView(badge, badgeLayoutParams)
                }
            }
        })

//        badge.post {
//
//            Log.d("badge", "(left, top, right, bottom) : (${badge.left}, ${badge.top}, ${badge.right}, ${badge.bottom})")
//            Log.d("badge", "badge height: ${badge.height} px -> ${UI.pxToDp(this@BottomNavigationActivity, badge.height.toFloat())} dp")
//            Log.d("badge", "badge width: ${badge.width} px -> ${UI.pxToDp(this@BottomNavigationActivity, badge.width.toFloat())} dp")
//
//            val badgeLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(badge.layoutParams).apply {
//                gravity = Gravity.CENTER_HORIZONTAL
//                leftMargin = UI.pxToDp(this@BottomNavigationActivity, badge.width / 2f).toInt()
//            }
//
//            bottomSelectedMenuItem?.addView(badge, badgeLayoutParams)
//        }


    }

    private fun addBadge2(menuItemId: Int) {
        val bottomNavigationMenu = material_bottomnav.getChildAt(0) as? BottomNavigationMenuView
        val bottomSelectedMenuItem = bottomNavigationMenu?.findViewById<BottomNavigationItemView>(menuItemId)

        badge = LayoutInflater.from(this).inflate(R.layout.badge_layout, bottomNavigationMenu, false)
        val badgeLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(badge.layoutParams).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            leftMargin = UI.pxToDp(this@BottomNavigationActivity, badge.width / 2f).toInt()
            //leftMargin = UI.dpToPx(this@BottomNavigationActivity, 20f).toInt()
        }

        bottomSelectedMenuItem?.addView(badge, badgeLayoutParams)
    }

    private fun addBadge3(menuItemId: Int, number: Int) {
        material_bottomnav.getOrCreateBadge(menuItemId)
        material_bottomnav.getBadge(menuItemId)?.number = number
    }
}
