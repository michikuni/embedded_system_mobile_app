package com.company.smartcarpark.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.smartcarpark.R
import com.company.smartcarpark.data.model.Vehicle

class AvailableVehiclesAdapter (private var vehicles: List<Vehicle>) :
    RecyclerView.Adapter<AvailableVehiclesAdapter.VehicleViewHolder>() {
    // ViewHolder để ánh xạ các thành phần giao diện của từng mục
    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val availableSpace: TextView = itemView.findViewById(R.id.text_available_spaces)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val occupied_percent: TextView = itemView.findViewById(R.id.text_occupied_percent)
    }

    // Tạo ViewHolder từ file XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home, parent, false)
        return VehicleViewHolder(view)
    }
    // Gắn dữ liệu từ danh sách vào ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.availableSpace.text = "Available Spaces ${100 - vehicles.size}"
        holder.occupied_percent.text = "${vehicles.size} % occupied"
        holder.progressBar.progress = vehicles.size
    }
    fun updateAvailableSpaces(newVehicles: List<Vehicle>) {
        vehicles = newVehicles // Cập nhật danh sách mới
        notifyDataSetChanged() // Thông báo adapter cập nhật toàn bộ dữ liệu
    }

    // Trả về số lượng phần tử trong danh sách
    override fun getItemCount(): Int = vehicles.size
}