package com.almaz.cvss

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_v2.*
import kotlinx.android.synthetic.main.spinner_a.*
import kotlinx.android.synthetic.main.spinner_ac.*
import kotlinx.android.synthetic.main.spinner_au.*
import kotlinx.android.synthetic.main.spinner_av.*
import kotlinx.android.synthetic.main.spinner_c.*
import kotlinx.android.synthetic.main.spinner_i.*
import kotlinx.android.synthetic.main.spinner_pr.*
import kotlinx.android.synthetic.main.spinner_s.*
import kotlinx.android.synthetic.main.spinner_ui.*
import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

class V3Fragment : Fragment() {

    private var AV = 0.85
    private var AC = 0.77
    private var PR = 0.85
    private var UI = 0.85
    private var S = 0
    private var C = 0.0
    private var I = 0.0
    private var A = 0.0
    private var baseScore = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_v3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSpinnersTitles()
        setUpSpinnersData()
        setUpSpinnersListeners()

        btn_count.setOnClickListener {
            val impactBase = 1.0 - (1.0 - C) * (1.0 - I) * (1.0 - A)
            val exploitability = 8.22 * AV * AC * PR * UI
            var impact = 0.0
            when(S) {
                0 -> impact = 6.42 * impactBase
                1 -> impact = 7.52 * (impactBase - 0.029) - 3.25 * (impactBase - 0.02).pow(15.0)
            }

            if (impact <= 0) {
                baseScore = 0.0
            } else {
                when(S) {
                    0 -> baseScore = ceil(min((impact + exploitability), 10.0) * 10.0) / 10.0
                    1 -> baseScore = ceil(min(1.08 * (impact + exploitability), 10.0) * 10.0) / 10.0
                }
            }
            tv_result.text = "BaseScore = $baseScore"
        }
    }

    private fun setUpSpinnersTitles() {
        spinner_title_av.text = "Вектор атаки (AV)"
        spinner_title_ac.text = "Сложность атаки (AC)"
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
                    0 -> AV = 0.85
                    1 -> AV = 0.62
                    2 -> AV = 0.55
                    3 -> AV = 0.2
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
                    0 -> AC = 0.77
                    1 -> AC = 0.44
                }
            }
        }

        spinner_s?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> S = 0
                    1 -> S = 1
                }
            }
        }

        spinner_pr?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> PR = 0.85
                    1 -> PR = if(S == 1) 0.68 else 0.62
                    2 -> PR = if(S == 1) 0.5 else 0.27
                }
            }
        }

        spinner_ui?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (position) {
                    0 -> UI = 0.85
                    1 -> UI = 0.62
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
                    1 -> C = 0.22
                    2 -> C = 0.56
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
                    1 -> I = 0.22
                    2 -> I = 0.56
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
                    1 -> A = 0.22
                    2 -> A = 0.56
                }
            }
        }
    }

    private fun setUpSpinnersData() {
        var arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Сетевой (N)", "Смежная сеть (A)", "Локальный (L)", "Физический (P)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_av.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Высокая (H)", "Низкая (L)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_ac.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Высокий (H)", "Низкий (L)", "Не требуется (N)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_pr.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Требуется (R)", "Не требуется (N)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_ui.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (U)", "Оказывает (C)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_s.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (N)", "Низкие (L)", "Высокое (H)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_c.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (N)", "Низкие (L)", "Высокое (H)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_i.adapter = arrayAdapter

        arrayAdapter = ArrayAdapter(
                activity as Activity,
                android.R.layout.simple_spinner_item,
                arrayListOf("Не оказывает (N)", "Низкие (L)", "Высокое (H)")
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_a.adapter = arrayAdapter
    }
}