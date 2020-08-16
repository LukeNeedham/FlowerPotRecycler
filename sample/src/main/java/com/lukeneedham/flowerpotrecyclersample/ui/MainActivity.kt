package com.lukeneedham.flowerpotrecyclersample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.lukeneedham.flowerpotrecyclersample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.findNavController().navigateUp() || super.onSupportNavigateUp()
    }
}
