package com.pilambda.findmerenew.Actividades

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import com.pilambda.findmerenew.Fragments.ConfiguracionFragment
import com.pilambda.findmerenew.Fragments.HomeFragment
import com.pilambda.findmerenew.R
import com.pilambda.findmerenew.R.id.toolbarMenu

/***
 * @author Kevin Alexis
 */
class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    //------------------------
    //    UserInterface
    //------------------------
    private var mTextMessage: TextView? = null
    private var mManager: FragmentManager? = null
    private var mNavigation : BottomNavigationView? = null
    //------------------------
    //    Member Variables
    //------------------------
    private var mFragmentConfiguracion: Fragment? = null
    private var mFragmentMenu :Fragment? = null

    //------------------------
    //    OverridenFunctions
    //------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolBar = findViewById<android.support.v7.widget.Toolbar?>(R.id.toolbarMenu)
        setSupportActionBar(toolBar)
        mManager = supportFragmentManager
        mFragmentConfiguracion = ConfiguracionFragment()
        mFragmentMenu = HomeFragment()
        val transaction = mManager!!.beginTransaction()
        transaction.replace(R.id.fragment_container_menu,mFragmentMenu)
        transaction.commit()
        mTextMessage = findViewById(R.id.message)
        mNavigation = findViewById(R.id.navigation)
        mNavigation?.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val transaction = mManager!!.beginTransaction()
        when (id) {
            R.id.navigation_configuracion -> {
                transaction.replace(R.id.fragment_container_menu, mFragmentConfiguracion)
            }
            R.id.navigation_home -> {
                transaction.replace(R.id.fragment_container_menu,mFragmentMenu)
            }
        }
        transaction.commit()
        return true
    }
}
