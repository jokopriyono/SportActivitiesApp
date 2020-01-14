package com.rizzkijo.sportactivitiesapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    companion object {
        private const val TAG_HOME = "tag_home"
        private const val TAG_PROFILE = "tag_profile"
    }

    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchHighlight(0)
        initFragments()

        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragments() {
        homeFragment = HomeFragment()
        profileFragment = ProfileFragment()
        currentFragment = homeFragment

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frame_layout, homeFragment, TAG_HOME).show(homeFragment)
        ft.add(R.id.frame_layout, profileFragment, TAG_PROFILE).hide(profileFragment)
        ft.commit()
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_1 -> if (bottom_navigation.selectedItemId != R.id.menu_1) {
                switchHighlight(0)
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )
                    .hide(currentFragment)
                    .show(homeFragment).commit()
                currentFragment = homeFragment
            }
            R.id.menu_2 -> switchHighlight(1)
            R.id.menu_3 -> switchHighlight(2)
            R.id.menu_4 -> if (bottom_navigation.selectedItemId != R.id.menu_4) {
                switchHighlight(3)
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )
                    .hide(currentFragment)
                    .show(profileFragment).commit()
                currentFragment = profileFragment
            }
        }
        return true
    }
}
