package com.example.skeletonapp

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skeletonapp.databinding.ActivityDrawerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val currentInsetTypes = mutableSetOf<Int>()
    private var currentWindowInsets: WindowInsetsCompat = WindowInsetsCompat.Builder().build()

    private lateinit var binding: ActivityDrawerBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        Log.d("TAG", ">>> MainActivity created")

//        val window = this.window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.statusBarColor = this.resources.getColor(R.color.teal_700)

        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
//            currentWindowInsets = windowInsets
//            applyInsets()
//        }

        setSupportActionBar(binding.mainContent.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        val bottomNavView: BottomNavigationView = binding.mainContent.bottomNavView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavView.setupWithNavController(navController)

        binding.navView.menu.findItem(R.id.navigation_login)
            .setOnMenuItemClickListener { menuItem: MenuItem? ->
                //write your implementation here
                //to close the navigation drawer
//            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//                drawer_layout.closeDrawer(GravityCompat.START)
//            }
                viewModel.toggleSessionState()
                binding.drawerLayout.closeDrawer(GravityCompat.START)

                true
            }

        lifecycleScope.launch {
            viewModel.sessionFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {

                    binding.navView.menu.findItem(R.id.navigation_login).title =
                        if (it) "Log Out" else "Log In"

                    if (!it) {
                        showAlert("Session is over")
                    }
                }
        }
    }

    private fun showAlert(message: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(message)
        builder.setTitle("Warning")
        builder.setCancelable(false)

        builder.setPositiveButton(
            "Log in"
        ) { dialog: DialogInterface, which: Int ->
            viewModel.toggleSessionState()
            dialog.cancel()
        }

        builder.setNegativeButton(
            "Finish"
        ) { dialog: DialogInterface, which: Int ->
            finish()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()

//        if (!viewModel.isLoggedIn()) {
//            Toast.makeText(
//                applicationContext,
//                "Logged out in Feature module",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu
        // this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun applyInsets(): WindowInsetsCompat {
        val currentInsetTypeMask = currentInsetTypes.fold(0) { accumulator, type ->
            accumulator or type
        }
        val insets = currentWindowInsets.getInsets(currentInsetTypeMask)
        binding.root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            updateMargins(insets.left, insets.top, insets.right, insets.bottom)
        }
        return WindowInsetsCompat.Builder()
            .setInsets(currentInsetTypeMask, insets)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("TAG", ">>> MainActivity destroyed")
    }
}
