package com.company.smartcarpark.utils

import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.smartcarpark.R
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.company.smartcarpark.data.model.Vehicle

class VehiclesAdapter(private var vehicles: List<Vehicle>) :
    RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder>() {
    private var ServerUrl: String? = null
    private val serverRepository = ServerRepository()

    init {
        serverRepository.server_url.observeForever { url ->
            ServerUrl = url
        }

        serverRepository.fetchServerUrl()
    }
    // ViewHolder để ánh xạ các thành phần giao diện của từng mục
    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val licensePlateTextView: TextView = itemView.findViewById(R.id.text_latest_license_plate_render)
        val entryTimeTextView: TextView = itemView.findViewById(R.id.text_entry_time_latest)
        val exitTimeTextView: TextView = itemView.findViewById(R.id.text_exit_time_latest)
        val imageView: ImageView = itemView.findViewById(R.id.image_latest_license_plate)
    }

    // Tạo ViewHolder từ file XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle_latest, parent, false)
        return VehicleViewHolder(view)
    }

    // Gắn dữ liệu từ danh sách vào ViewHolder
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.licensePlateTextView.text = vehicle.licensePlate
        holder.entryTimeTextView.text = vehicle.entryTime
        holder.exitTimeTextView.text = vehicle.exitTime
        // Sử dụng thư viện như Glide hoặc Picasso để tải ảnh
        Glide.with(holder.imageView.context)
            .load("${ServerUrl}${vehicle.imageSrc}")
            .into(holder.imageView)
    }
    fun updateData(newVehicles: List<Vehicle>) {
        // Tạo MutableList mới để chứa dữ liệu mới
        val mutableVehicles = vehicles.toMutableList()
        mutableVehicles.clear()
        mutableVehicles.addAll(newVehicles)
        // Gán lại danh sách cho `vehicles`
        vehicles = mutableVehicles
        notifyDataSetChanged()
    }

    // Trả về số lượng phần tử trong danh sách
    override fun getItemCount(): Int = vehicles.size
}

