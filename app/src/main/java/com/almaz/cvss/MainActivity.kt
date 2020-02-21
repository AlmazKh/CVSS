package com.almaz.cvss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.spinner_a.*
import kotlinx.android.synthetic.main.spinner_ac.*
import kotlinx.android.synthetic.main.spinner_au.*
import kotlinx.android.synthetic.main.spinner_av.*
import kotlinx.android.synthetic.main.spinner_c.*
import kotlinx.android.synthetic.main.spinner_i.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private var AV = 0.395
    private var AC = 0.35
    private var Au = 0.45
    private var C = 0.0
    private var I = 0.0
    private var A = 0.0
    private var baseScore = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpSpinnersData()
        setUpSpinnersListeners()

        btn_count.setOnClickListener {
            val impact = 10.41 * (1.0 - (1.0 - C) * (1.0 - I) * (1.0 - A))
            val exploitability = 20 * AV * AC * Au
            val f = if (impact == 0.0) 0.0 else 1.176
            baseScore = round(((0.6 * impact) + (0.4 * exploitability) - 1.5) * f * 10.0) / 10.0
            tv_result.text = "BaseScore = $baseScore"
//            tv_result.textSize = R.dimen.font_large_24.toFloat()
        }
    }

    private fun setUpSpinnersListeners() {
        spinner_av?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> AV = 0.395
                    1 -> AV = 0.646
                    2 -> AV = 1.0
                }
            }
        }

        spinner_ac?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> AC = 0.35
                    1 -> AC = 0.61
                    2 -> AC = 0.71
                }
            }
        }

        spinner_au?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> Au = 0.45
                    1 -> Au = 0.56
                    2 -> Au = 0.704
                }
            }
        }

        spinner_c?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> C = 0.0
                    1 -> C = 0.275
                    2 -> C = 0.66
                }
            }
        }

        spinner_i?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> I = 0.0
                    1 -> I = 0.275
                    2 -> I = 0.66
                }
            }
        }

        spinner_a?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> A = 0.0
                    1 -> A = 0.275
                    2 -> A = 0.66
                }
            }
        }
    }

    private fun setUpSpinnersData() {
        var arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayListOf("Локальный (L)", "Смежная сеть (A)", "Сетевой (N)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_av.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayListOf("Высокая (H)", "Средняя (M)", "Низкая (L)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_ac.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayListOf("Множественная (M)", "Единственная (S)", "Не требуется (N)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_au.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (N)", "Частичное (P)", "Полное (C)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_c.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (N)", "Частичное (P)", "Полное (C)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_i.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (N)", "Частичное (P)", "Полное (C)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_a.adapter = arrayAdapter
    }
}
