package com.rizzkijo.sportactivitiesapp

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchHighlight(0)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, HomeFragment())
        ft.commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_1 -> switchHighlight(0)
                R.id.menu_2 -> switchHighlight(1)
                R.id.menu_3 -> switchHighlight(2)
                R.id.menu_4 -> switchHighlight(3)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun switchHighlight(pos: Int) {
        highlight_1.visibility = INVISIBLE
        highlight_2.visibility = INVISIBLE
        highlight_3.visibility = INVISIBLE
        highlight_4.visibility = INVISIBLE
        when (pos) {
            0 -> highlight_1.visibility = VISIBLE
            1 -> highlight_2.visibility = VISIBLE
            2 -> highlight_3.visibility = VISIBLE
            else -> highlight_4.visibility = VISIBLE
        }
    }
}
