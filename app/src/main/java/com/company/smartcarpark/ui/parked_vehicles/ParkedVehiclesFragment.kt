package com.company.smartcarpark.ui.parked_vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.smartcarpark.databinding.FragmentParkedVehiclesBinding
import com.company.smartcarpark.ui.viewmodel.ParkedVehiclesViewModel
import com.company.smartcarpark.utils.AllVehiclesAdapter

class ParkedVehiclesFragment : Fragment() {
    private lateinit var viewModel: ParkedVehiclesViewModel
    private lateinit var adapter: AllVehiclesAdapter
    private lateinit var binding: FragmentParkedVehiclesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParkedVehiclesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo ViewModel
        viewModel = ViewModelProvider(this)[ParkedVehiclesViewModel::class.java]

        // Thiết lập RecyclerView
        setupRecyclerView()

        // Observe dữ liệu từ ViewModel
        observeVehicles()
    }

    private fun setupRecyclerView() {
        adapter = AllVehiclesAdapter(emptyList())
        binding.recyclerViewParkedVehicles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ParkedVehiclesFragment.adapter
        }
    }

    private fun observeVehicles() {
        viewModel.all_vehicles_data.observe(viewLifecycleOwner) { vehicles ->
            adapter.updateAllData(vehicles)
        }
    }
}
