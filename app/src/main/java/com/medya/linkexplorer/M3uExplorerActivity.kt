package com.medya.linkexplorer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class M3uExplorerActivity : AppCompatActivity() {

    private lateinit var edtM3uUrl: EditText
    private lateinit var btnSelectFile: Button
    private lateinit var btnAnalyze: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m3u_explorer)

        edtM3uUrl = findViewById(R.id.edtM3uUrl)
        btnSelectFile = findViewById(R.id.btnSelectFile)
        btnAnalyze = findViewById(R.id.btnAnalyze)

        btnSelectFile.setOnClickListener {

            Toast.makeText(
                this,
                "Dosya seçme özelliği bir sonraki adımda eklenecek.",
                Toast.LENGTH_SHORT
            ).show()

        }

        btnAnalyze.setOnClickListener {

            val url = edtM3uUrl.text.toString().trim()

            if (url.isEmpty()) {

                edtM3uUrl.error = "Lütfen M3U URL girin."
                return@setOnClickListener

            }

            Toast.makeText(
                this,
                "Analiz başlatılıyor...",
                Toast.LENGTH_SHORT
            ).show()

        }

    }
}
