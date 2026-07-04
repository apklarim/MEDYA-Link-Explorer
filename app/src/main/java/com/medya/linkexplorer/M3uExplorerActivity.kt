package com.medya.linkexplorer

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.medya.linkexplorer.repository.CrawlerRepository
import kotlinx.coroutines.launch

class M3uExplorerActivity : AppCompatActivity() {

    private lateinit var edtKeyword: EditText
    private lateinit var edtMaxPages: EditText

    private lateinit var chkM3u: CheckBox
    private lateinit var chkM3u8: CheckBox
    private lateinit var chkXtream: CheckBox
    private lateinit var chkMag: CheckBox

    private lateinit var btnStartScan: Button

    private lateinit var progressBar: ProgressBar

    private lateinit var txtStatus: TextView

    private lateinit var listResults: ListView

    private lateinit var adapter: ArrayAdapter<String>

    private val resultList = mutableListOf<String>()

    private val repository = CrawlerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_m3u_explorer)

        edtKeyword = findViewById(R.id.edtKeyword)
        edtMaxPages = findViewById(R.id.edtMaxPages)

        chkM3u = findViewById(R.id.chkM3u)
        chkM3u8 = findViewById(R.id.chkM3u8)
        chkXtream = findViewById(R.id.chkXtream)
        chkMag = findViewById(R.id.chkMag)

        btnStartScan = findViewById(R.id.btnStartScan)

        progressBar = findViewById(R.id.progressBar)

        txtStatus = findViewById(R.id.txtStatus)

        listResults = findViewById(R.id.listResults)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resultList
        )

        listResults.adapter = adapter

        btnStartScan.setOnClickListener {
            startScan()
        }
    }

    private fun startScan() {

        resultList.clear()
        adapter.notifyDataSetChanged()

        progressBar.isIndeterminate = true

        txtStatus.text = "Tarama başlatılıyor..."

        val keyword = edtKeyword.text.toString().trim()

        val maxPages =
            edtMaxPages.text.toString().toIntOrNull() ?: 100

        lifecycleScope.launch {

            try {

                val links = repository.startScan(
                    startUrl = "https://example.com",
                    maxPages = maxPages
                )

                progressBar.isIndeterminate = false
                progressBar.progress = 100

                links.forEach { link ->

                    val type = link.type.name

                    if (type == "M3U" && !chkM3u.isChecked) return@forEach
                    if (type == "M3U8" && !chkM3u8.isChecked) return@forEach
                    if (type == "XTREAM" && !chkXtream.isChecked) return@forEach
                    if (type == "MAG" && !chkMag.isChecked) return@forEach

                    if (keyword.isBlank() ||
                        link.url.contains(keyword, true)
                    ) {

                        resultList.add(
                            "$type\n${link.url}"
                        )

                    }
                }

                adapter.notifyDataSetChanged()

                txtStatus.text =
                    "Bulunan bağlantı: ${resultList.size}"

            } catch (e: Exception) {

                progressBar.isIndeterminate = false

                txtStatus.text =
                    "Hata: ${e.message}"

            }

        }

    }

}
