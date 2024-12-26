package com.company.smartcarpark.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.smartcarpark.databinding.FragmentHomeBinding
import com.company.smartcarpark.ui.viewmodel.HomeViewModel
import com.company.smartcarpark.utils.VehiclesAdapter

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = VehiclesAdapter(emptyList())
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHome.adapter = adapter

        viewModel.fetchVehiclesData()
        viewModel.vehicles_data_latest.observe(viewLifecycleOwner){ vehicleList->
            adapter.updateData(vehicleList)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
