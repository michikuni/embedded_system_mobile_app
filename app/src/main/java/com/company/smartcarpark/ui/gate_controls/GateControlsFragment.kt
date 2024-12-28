package com.company.smartcarpark.ui.gate_controls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.company.smartcarpark.R
import com.company.smartcarpark.ui.viewmodel.GateControlsViewModel

class GateControlsFragment : Fragment() {

    private val gateControlsViewModel: GateControlsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gate_controls, container, false)
        gateControlsViewModel.fetchStatus()
        val buttonDoor1: Button = root.findViewById(R.id.button_gate_1)
        val buttonDoor2: Button = root.findViewById(R.id.button_gate_2)

        gateControlsViewModel.door1State.observe(viewLifecycleOwner, Observer { isOpen ->
            buttonDoor1.text = if (isOpen) "DOOR 1: OPEN" else "DOOR 1: CLOSED"
        })

        gateControlsViewModel.door2State.observe(viewLifecycleOwner, Observer { isOpen ->
            buttonDoor2.text = if (isOpen) "DOOR 2: OPEN" else "DOOR 2: CLOSED"
        })

        buttonDoor1.setOnClickListener {
            gateControlsViewModel.toggleDoor1()
        }

        buttonDoor2.setOnClickListener {
            gateControlsViewModel.toggleDoor2()
        }

        return root
    }
}
