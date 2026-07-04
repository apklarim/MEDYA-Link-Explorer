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

class MainActivity : AppCompatActivity() {

    private lateinit var editKeyword: EditText
    private lateinit var editMaxPages: EditText

    private lateinit var checkM3U: CheckBox
    private lateinit var checkM3U8: CheckBox
    private lateinit var checkXtream: CheckBox
    private lateinit var checkMAG: CheckBox

    private lateinit var buttonStart: Button

    private lateinit var progressBar: ProgressBar

    private lateinit var textStatus: TextView

    private lateinit var listResults: ListView

    private lateinit var adapter: ArrayAdapter<String>

    private val results = mutableListOf<String>()

    private val repository = CrawlerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        editKeyword = findViewById(R.id.editKeyword)
        editMaxPages = findViewById(R.id.editMaxPages)

        checkM3U = findViewById(R.id.checkM3U)
        checkM3U8 = findViewById(R.id.checkM3U8)
        checkXtream = findViewById(R.id.checkXtream)
        checkMAG = findViewById(R.id.checkMAG)

        buttonStart = findViewById(R.id.buttonStart)

        progressBar = findViewById(R.id.progressBar)

        textStatus = findViewById(R.id.textStatus)

        listResults = findViewById(R.id.listResults)

        adapter = ArrayAdapter(

            this,

            android.R.layout.simple_list_item_1,

            results

        )

        listResults.adapter = adapter

        buttonStart.setOnClickListener {

            startScan()

        }

    }

    private fun startScan() {

        results.clear()

        adapter.notifyDataSetChanged()

        progressBar.progress = 0

        progressBar.isIndeterminate = true

        textStatus.text = "Tarama başlatılıyor..."

        val keyword = editKeyword.text.toString().trim()

        val maxPages = editMaxPages.text.toString()

            .toIntOrNull() ?: 100

        lifecycleScope.launch {

            try {

                /*
                 * Geçici başlangıç URL'si.
                 *
                 * Bir sonraki pakette SearchEngine
                 * bunu otomatik sağlayacak.
                 */

                val links = repository.startScan(

                    startUrl = "https://example.com",

                    maxPages = maxPages

                )

                progressBar.isIndeterminate = false

                progressBar.progress = 100

                links.forEach {

                    val type = it.type.name

                    if (type == "M3U" && !checkM3U.isChecked) return@forEach
                    if (type == "M3U8" && !checkM3U8.isChecked) return@forEach
                    if (type == "XTREAM" && !checkXtream.isChecked) return@forEach
                    if (type == "MAG" && !checkMAG.isChecked) return@forEach

                    if (
                        keyword.isBlank() ||
                        it.url.contains(keyword, true)
                    ) {

                        results.add(

                            "$type\n${it.url}"

                        )

                    }

                }

                adapter.notifyDataSetChanged()

                textStatus.text =

                    "Bulunan bağlantı : ${results.size}"

            } catch (e: Exception) {

                progressBar.isIndeterminate = false

                textStatus.text =

                    "Hata : ${e.message}"

            }

        }

    }

}
