package com.example.dobcalulatornew

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity2 : AppCompatActivity() {

     private var tvSelectedDate: TextView?= null
     private var tv_DisplayMinutes: TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate= findViewById(R.id.tvSelectedDate)
        tv_DisplayMinutes = findViewById(R.id.tv_DisplayMinutes)
        
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

     }
    fun clickDatePicker(){//this function is used to select date

        val myCalendar= Calendar.getInstance()//this will give access to calendar, year , date, month etc
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)
        //DatePickerDialog is used to display the calendar and select the date
        val dpd=DatePickerDialog(this,
        DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->


            val selectedDate= "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            //so far we dont have date in data format so that we can use it and do some calculation
            //in order to do that we will use SimpleDateFormat class, using this class we will convert
            // the date in a format so that we can perfom some calculations on it.

            val sdf=    SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)// there are specific meaning for mm,MM etc, see the documentation
            val theDate= sdf.parse(selectedDate) //The parse() Method of SimpleDateFormat class is
                                           // used to parse the text from a string to produce the Date.
            //val selectedDateInMinutes= theDate.time /60000  //.time==getTime() which is a method which gives the time in millisecond
            theDate?.let{    //.let is used so as to make theDate null safe i.e, it will execute only if the theDate is no null
                val currentDate= Date()
                currentDate?.let {
                    val timeDifferenceMillis = currentDate.time - theDate.time
                    //current date


                    val totalMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis)
                    tv_DisplayMinutes?.text = totalMinutes.toString()
                }

                Toast.makeText(this, "Minutes Calculated successfull", Toast.LENGTH_SHORT).show()
            }

        },
            year,
            month,
            day

        )
        // Set the maximum date allowed to the current date
        dpd.datePicker.maxDate = myCalendar.timeInMillis
        dpd.show()


    }
}