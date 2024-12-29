package com.company.smartcarpark.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.company.smartcarpark.R
import com.company.smartcarpark.data.model.Vehicle
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.app.AlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AllVehiclesAdapter (private var vehicles: List<Vehicle>, private val context: Context) :
    RecyclerView.Adapter<AllVehiclesAdapter.VehicleViewHolder>() {
        val ServerUrl: String = "https://ae47-14-162-134-8.ngrok-free.app"
    // ViewHolder để ánh xạ các thành phần giao diện của từng mục
    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberIndex: TextView = itemView.findViewById(R.id.text_serial)
        val licensePlateTextView: TextView = itemView.findViewById(R.id.text_license_plate)
        val entryTimeTextView: TextView = itemView.findViewById(R.id.text_entry_time)
        val exitTimeTextView: TextView = itemView.findViewById(R.id.text_exit_time)
        val buttonViewImage: Button = itemView.findViewById(R.id.button_view_image)
        val buttonExit: Button = itemView.findViewById(R.id.button_exit)
    }

    private val database = FirebaseDatabase.getInstance().reference
    // Tạo ViewHolder từ file XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }
    // Gắn dữ liệu từ danh sách vào ViewHolder
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]
        holder.numberIndex.text = (position +1).toString()
        holder.licensePlateTextView.text = vehicle.licensePlate
        holder.entryTimeTextView.text = convertDateFormat(vehicle.entryTime)
        holder.exitTimeTextView.text = convertDateFormat(vehicle.exitTime)
        holder.buttonViewImage.setOnClickListener {
            val imageSrc = "${ServerUrl}${vehicle.imageSrc}"
            showImageDialog(imageSrc)
        }
        holder.buttonExit.setOnClickListener {
            val exitTime = getCurrentTime()
            updateExitTimeToFirebase(vehicle.licensePlate, exitTime)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateFormat(dateString: String): String {
        // Định dạng đầu vào
        return if (dateString.contains('T')) {
            // Định dạng đầu vào với phần microseconds
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            // Chuyển đổi chuỗi thành LocalDateTime
            val dateTime = LocalDateTime.parse(dateString, inputFormatter)

            // Định dạng đầu ra không có phần microseconds
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            // Trả về chuỗi sau khi định dạng lại
            dateTime.format(outputFormatter)
        } else {
            // Nếu chuỗi đã có định dạng yyyy-MM-dd HH:mm:ss thì giữ nguyên
            dateString
        }
    }

    private fun updateExitTimeToFirebase(licensePlate: String, exitTime: String) {
        val ref = database.child("vehicles").child(licensePlate)
        ref.child("exitTime").setValue(exitTime)
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
    private fun showImageDialog(imageUrl: String) {
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog_image, null)

        val imageView = dialogView.findViewById<ImageView>(R.id.dialogImageView)
        val progressBar = dialogView.findViewById<ProgressBar>(R.id.dialogProgressBar)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Xem ảnh")
            .setPositiveButton("Đóng") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        // Load ảnh sử dụng Glide
        progressBar.visibility = View.VISIBLE
        Glide.with(context)
            .load(imageUrl)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Lỗi tải ảnh", Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)

        dialog.show()
    }

    fun updateAllData(newVehicles: List<Vehicle>) {
        vehicles = newVehicles // Cập nhật danh sách mới
        notifyDataSetChanged() // Thông báo adapter cập nhật toàn bộ dữ liệu
    }

    // Trả về số lượng phần tử trong danh sách
    override fun getItemCount(): Int = vehicles.size
}