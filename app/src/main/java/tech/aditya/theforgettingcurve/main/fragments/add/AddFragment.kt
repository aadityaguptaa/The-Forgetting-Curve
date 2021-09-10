package tech.aditya.theforgettingcurve.main.fragments.add

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import tech.aditya.theforgettingcurve.R
import tech.aditya.theforgettingcurve.databinding.FragmentAddBinding
import java.util.*


class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding

    var states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_enabled)

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add, container, false
        )

        var colors = intArrayOf(
            resources.getColor(R.color.blue_600),
            Color.BLACK

        )

        var myList = ColorStateList(states, colors)

        binding.selectDurationTextView.setOnClickListener { v: View ->
            showMenu(v, R.menu.menu1)
        }
        binding.allDaySwitch.thumbTintList = myList

        binding.allDaySwitch.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked){
                binding.timeTextView.visibility = View.VISIBLE
                binding.timeTextView.isClickable = true
            }else{
                binding.timeTextView.visibility = View.INVISIBLE
                binding.timeTextView.isClickable = false
            }
        }

        binding.timeTextView.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Appointment time")
                    .build()
            picker.show(this.parentFragmentManager, "TAG");

            picker.addOnPositiveButtonClickListener {
                var hour = picker.hour
                var minute = picker.minute
                var zone = if(hour > 12) "PM" else "AM"
                hour %= 12
                binding.timeTextView.text = "$hour:$minute $zone"
            }
        }


        binding.dateTextView.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(this.parentFragmentManager, "tag");


            datePicker.addOnPositiveButtonClickListener {
                // Respond to positive button click.
                val _date = Date(datePicker.selection!!).toString()
                val day = _date.substring(0,3)
                val month = _date.substring(4,7)
                val date = _date.substring(8,10 )
                val year = _date.substring(_date.lastIndex-3, _date.lastIndex+1)
                binding.dateTextView.text = "$day, $date $month, $year"
            }

        }
        return binding.root
    }

    private fun showMenu(v: View, menu1: Int) {

        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menu1, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            binding.selectDurationTextView.text = menuItem.title
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

}