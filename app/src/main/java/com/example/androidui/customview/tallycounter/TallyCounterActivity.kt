package com.example.androidui.customview.tallycounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.view.ContextThemeWrapper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.androidui.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_tally_counter.*

class TallyCounterActivity : AppCompatActivity() {

    private var button: MaterialButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tally_counter)
        initButton()

        button?.setOnClickListener {
            tall_counter_view.increment()
        }
    }

    private fun initButton() {

        // define button
        button = MaterialButton(ContextThemeWrapper(this, R.style.Widget_MaterialComponents_Button))
        button?.id = R.id.btn_tally_counter_inc
        button?.text = "Increment"

        // define layoutParams
        /*val buttonLayoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        button?.layoutParams = buttonLayoutParams*/

        val set = ConstraintSet()
        set.clone(tally_counter_layout)
        set.constrainWidth(R.id.btn_tally_counter_inc, ConstraintSet.WRAP_CONTENT)
        set.constrainHeight(R.id.btn_tally_counter_inc, ConstraintSet.WRAP_CONTENT)
        set.connect(button!!.id, ConstraintSet.TOP, R.id.tall_counter_view, ConstraintSet.BOTTOM)
        set.connect(button!!.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        set.connect(button!!.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        // add button to root layout (constraintLayout)
        tally_counter_layout.addView(button!!)

        // apply constraints
        set.applyTo(tally_counter_layout)
    }
}
