package com.example.androidui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.viewpager_frame.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentViewPager : Fragment() {

    companion object {
        fun newInstance(textItem: String): Fragment {
            val fragment = FragmentViewPager()
            val bundle = Bundle()
            bundle.putString("textItem", textItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.viewpager_frame, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textItem = arguments?.getString("textItem")
        tvText.text = textItem
    }
}
