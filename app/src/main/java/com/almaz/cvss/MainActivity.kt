package com.almaz.cvss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(V2Fragment(), "V2")
        adapter.addFragment(V3Fragment(), "V3")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}
