package com.medya.linkexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnM3u = findViewById<Button>(R.id.btnM3u)

        btnM3u.setOnClickListener {
            startActivity(
                Intent(this, M3uExplorerActivity::class.java)
            )
        }
    }
}
