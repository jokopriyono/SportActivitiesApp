package com.rizzkijo.sportactivitiesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onResume() {
        super.onResume()
        Thread(Runnable {
            run {
                for (i in 0..26) {
                    activity?.runOnUiThread {
                        gauge_weight.setValue(i.toFloat())
                        txt_value_gauge.text = gauge_weight.getValue().toString()
                    }
                    Thread.sleep(25)
                }
            }
        }).start()
    }
}