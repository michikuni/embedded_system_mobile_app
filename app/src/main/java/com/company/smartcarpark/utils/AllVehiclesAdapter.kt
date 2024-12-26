package com.company.smartcarpark.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.smartcarpark.R
import com.company.smartcarpark.data.model.Vehicle

class AllVehiclesAdapter (private var vehicles: List<Vehicle>) :
    RecyclerView.Adapter<AllVehiclesAdapter.VehicleViewHolder>() {

    // ViewHolder để ánh xạ các thành phần giao diện của từng mục
    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberIndex: TextView = itemView.findViewById(R.id.text_serial)
        val licensePlateTextView: TextView = itemView.findViewById(R.id.text_license_plate)
        val entryTimeTextView: TextView = itemView.findViewById(R.id.text_entry_time)
        val exitTimeTextView: TextView = itemView.findViewById(R.id.text_exit_time)
    }

    // Tạo ViewHolder từ file XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }
    // Gắn dữ liệu từ danh sách vào ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.numberIndex.text = (position +1).toString()
        holder.licensePlateTextView.text = vehicle.licensePlate
        holder.entryTimeTextView.text = vehicle.entryTime
        holder.exitTimeTextView.text = vehicle.exitTime
    }
    fun updateAllData(newVehicles: List<Vehicle>) {
        vehicles = newVehicles // Cập nhật danh sách mới
        notifyDataSetChanged() // Thông báo adapter cập nhật toàn bộ dữ liệu
    }

    // Trả về số lượng phần tử trong danh sách
    override fun getItemCount(): Int = vehicles.size
}