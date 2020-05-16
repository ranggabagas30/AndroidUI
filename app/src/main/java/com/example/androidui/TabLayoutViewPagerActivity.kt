package com.example.androidui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.androidui.util.UI
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_tab_layout_view_pager.*

class TabLayoutViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_view_pager)

        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount - 1) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.marginEnd = UI.dpToPx(this, 8f).toInt()
                layoutParams.marginStart = UI.dpToPx(this, 8f).toInt()
            }
            tab.layoutParams = layoutParams
            tabLayout.requestLayout()
        }

        val textItems = arrayListOf(
            "Page 1",
            "Page 2",
            "Page 3",
            "Page 4",
            "Page 5",
            "Page 6",
            "Page 7",
            "Page 8"
        )

        viewPager2.adapter = TabLayoutViewPager2Adapter(textItems)

        TabLayoutMediator(tabLayout, viewPager2, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = textItems[position]
        }).attach()

        btnPrevious.setOnClickListener {
            if (viewPager2.currentItem > 0)
                viewPager2.setCurrentItem(viewPager2.currentItem - 1, true)
        }

        btnNext.setOnClickListener {
            if (viewPager2.currentItem < textItems.size - 1)
                viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
        }
    }
}
