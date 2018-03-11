package com.pilambda.findmerenew.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pilambda.findmerenew.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }, 2400)
    }
}
