package com.medya.linkexplorer

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.medya.linkexplorer.repository.CrawlerRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var editStartUrl: EditText
    private lateinit var editMaxPages: EditText
    private lateinit var buttonStart: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var textStatus: TextView
    private lateinit var listResults: ListView

    private lateinit var adapter: ArrayAdapter<String>

    private val resultList = mutableListOf<String>()

    private val repository = CrawlerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        editStartUrl = findViewById(R.id.editStartUrl)
        editMaxPages = findViewById(R.id.editMaxPages)
        buttonStart = findViewById(R.id.buttonStart)
        progressBar = findViewById(R.id.progressBar)
        textStatus = findViewById(R.id.textStatus)
        listResults = findViewById(R.id.listResults)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resultList
        )

        listResults.adapter = adapter

        buttonStart.setOnClickListener {

            startScan()

        }
    }

    private fun startScan() {

        resultList.clear()
        adapter.notifyDataSetChanged()

        progressBar.isIndeterminate = true

        textStatus.text = "Tarama başlatılıyor..."

        val url = editStartUrl.text.toString().trim()

        val maxPages = editMaxPages.text.toString()
            .toIntOrNull() ?: 100

        lifecycleScope.launch {

            val links = repository.startScan(
                url,
                maxPages
            )

            progressBar.isIndeterminate = false

            links.forEach {

                resultList.add(
                    "${it.type} : ${it.url}"
                )

            }

            adapter.notifyDataSetChanged()

            textStatus.text =
                "Bulunan bağlantı : ${links.size}"

        }

    }

}
