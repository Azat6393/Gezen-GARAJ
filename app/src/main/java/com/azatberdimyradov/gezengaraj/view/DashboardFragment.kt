package com.azatberdimyradov.gezengaraj.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.azatberdimyradov.gezengaraj.R
import com.azatberdimyradov.gezengaraj.viewmodel.CarsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.works_dialog.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    var yearC: Int = 0
    var monthC: Int = 0
    var dayC: Int = 0
    var dateString = ""
    var worksString = ""
    private lateinit var workDialog: Dialog

    private val viewModel by lazy { ViewModelProvider(this).get(CarsViewModel::class.java) }

    private val args by navArgs<DashboardFragmentArgs>()
    var carInfo = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        car_brand_text_view.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToCarBrandsFragment()
            findNavController().navigate(action)
        }

        if (args.carBrand != null){
            carInfo = "${args.carBrand} ${args.carModel} ${args.carYear}"
            car_brand_text_view.text = carInfo
        }

        //contact info
        val btnSheet = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null, false)
        val btnDialog = BottomSheetDialog(this.requireContext())
        btnDialog.setContentView(btnSheet)

        contact_info_text_view.setOnClickListener {
            btnDialog.show()
        }
        btnSheet.bottom_sheet_dialog_back_button.setOnClickListener {
            btnDialog.dismiss()
        }
        val context = context

        //select work
        workDialog = Dialog(context!!)
        workDialog.setContentView(R.layout.works_dialog)
        work_text_view.setOnClickListener {
            workDialog.show()
        }
        workDialog.ok_button.setOnClickListener {
            worksString = ""
            checkCheckBoxes()
            work_text_view.text = worksString
            workDialog.dismiss()
        }

        // select date
        val cal = Calendar.getInstance()
        val dpd = DatePickerDialog(context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // Display selected date in textBox
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        cal.set(year, month, dayOfMonth, hourOfDay, minute)
                        val timeInMillis = cal.timeInMillis
                        if (timeInMillis >= System.currentTimeMillis()) {
                            val sdf = SimpleDateFormat("dd MMMM yyyy  kk:mm")
                            dateString = sdf.format(timeInMillis)
                            date_text_view.text = dateString
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                TimePickerDialog(
                    context,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
            }, yearC, monthC, dayC
        )

        date_text_view.setOnClickListener {
            dpd.show()
            dpd.datePicker.minDate = System.currentTimeMillis()
        }

        //insert data to Firebase
        price_button.setOnClickListener {
            if (carInfo.equals("") ||
                name_surname_edit_text.text.toString().equals("") ||
                address_edit_text.text.toString().equals("") ||
                phone_number_edit_text.text.toString().equals("") ||
                worksString.equals("") ||
                dateString.equals("")){
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }else{
                val db = FirebaseFirestore.getInstance()
                val appointment = hashMapOf<String, String>()
                appointment.put("name", name_surname_edit_text.text.toString())
                appointment.put("car", carInfo)
                appointment.put("address", address_edit_text.text.toString())
                appointment.put("number", phone_number_edit_text.text.toString())
                appointment.put("work", worksString)
                appointment.put("date", dateString)
                db.collection("Appointment")
                    .add(appointment)
                    .addOnSuccessListener {
                        postNotification()
                        toast()
                        clear()
                    }.addOnFailureListener {
                        Toast.makeText(context, it.localizedMessage.toString(), Toast.LENGTH_LONG).show()
                    }
            }
        }
    }

    fun postNotification(){
        viewModel.readPlayerID()
        observe()
    }
    fun observe(){
        viewModel.readPlayerID().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            for (id in it) {
                println("id: $id")
                try {
                    OneSignal.postNotification(
                        JSONObject("{'contents': {'en':'Yeni Randevu'}, 'include_player_ids': ['" + id.playerID + "']}"),
                        null
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun clear(){
        name_surname_edit_text.setText("")
        car_brand_text_view.text = ""
        address_edit_text.setText("")
        phone_number_edit_text.setText("")
        work_text_view.text = ""
        date_text_view.text = ""
    }

    fun toast(){
        card_view.isVisible = true
        tamam_button.setOnClickListener {
            card_view.isVisible = false
        }
    }

    fun checkCheckBoxes() {
        if (workDialog.checkbox_one.isChecked) {
            worksString += "${workDialog.checkbox_one.text.toString()}/"
        }
        if (workDialog.checkbox_two.isChecked) {
            worksString += "${workDialog.checkbox_two.text.toString()}/"
        }
        if (workDialog.checkbox_three.isChecked) {
            worksString += "${workDialog.checkbox_three.text.toString()}/"
        }
        if (workDialog.checkbox_four.isChecked) {
            worksString += "${workDialog.checkbox_four.text.toString()}/"
        }
        if (workDialog.checkbox_five.isChecked) {
            worksString += "${workDialog.checkbox_five.text.toString()}/"
        }
        if (workDialog.checkbox_six.isChecked) {
            worksString += "${workDialog.checkbox_six.text.toString()}/"
        }
    }

}