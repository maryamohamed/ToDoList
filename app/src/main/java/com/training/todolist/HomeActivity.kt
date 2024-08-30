package com.training.todolist

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // Set up the toggle for the drawer layout
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set navigation item selection listener
        navigationView.setNavigationItemSelectedListener(this)

        // Load the fragment that shows all tasks by default
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment.newInstance(null))
            navigationView.setCheckedItem(R.id.nav_all)
        }
    }

    // Method to replace the current fragment with a new one
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Add to back stack to allow back navigation
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_all -> replaceFragment(HomeFragment.newInstance(null)) // Show all tasks
            R.id.nav_Personal -> replaceFragment(HomeFragment.newInstance("Personal"))
            R.id.nav_home -> replaceFragment(HomeFragment.newInstance("Home"))
            R.id.nav_work -> replaceFragment(HomeFragment.newInstance("Work"))
            // Add more cases if you have other menu items
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() // Navigate back in the fragment stack
        } else {
            super.onBackPressed()
        }
    }
}
