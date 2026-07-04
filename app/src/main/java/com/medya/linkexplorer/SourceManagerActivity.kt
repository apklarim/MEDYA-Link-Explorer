package com.medya.linkexplorer

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.medya.linkexplorer.search.SearchEngine

class SourceManagerActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtUrl: EditText

    private lateinit var btnAdd: Button

    private lateinit var listSources: ListView

    private lateinit var adapter: ArrayAdapter<String>

    private val searchEngine = SearchEngine()

    private val sourceNames = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_source_manager)

        edtName = findViewById(R.id.edtSourceName)
        edtUrl = findViewById(R.id.edtSourceUrl)

        btnAdd = findViewById(R.id.btnAddSource)

        listSources = findViewById(R.id.listSources)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            sourceNames
        )

        listSources.adapter = adapter

        refreshList()

        btnAdd.setOnClickListener {

            val name = edtName.text.toString().trim()
            val url = edtUrl.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(
                    this,
                    "Kaynak adı giriniz",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (url.isEmpty()) {
                Toast.makeText(
                    this,
                    "URL giriniz",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            searchEngine.addSource(
                name = name,
                url = url
            )

            edtName.text.clear()
            edtUrl.text.clear()

            refreshList()

            Toast.makeText(
                this,
                "Kaynak eklendi",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun refreshList() {

        sourceNames.clear()

        searchEngine
            .getSources()
            .forEach {

                sourceNames.add(
                    "${it.name}\n${it.url}"
                )

            }

        adapter.notifyDataSetChanged()
    }
}
