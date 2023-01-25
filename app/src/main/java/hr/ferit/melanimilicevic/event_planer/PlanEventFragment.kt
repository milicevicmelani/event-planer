package hr.ferit.melanimilicevic.event_planer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class PlanEventFragment : Fragment() {
    private val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plan_event, container, false)

        val buttonHome=view.findViewById<Button>(R.id.buttonHome)
        val buttonEvents=view.findViewById<Button>(R.id.buttonEventsList)

        val homeFragment = HomeFragment()
        val eventsListFragment = EventsListFragment()


        buttonHome.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.planEventFrame, homeFragment)
            fragmentTransaction?.commit()
        }

        buttonEvents.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.planEventFrame, eventsListFragment)
            fragmentTransaction?.commit()
        }

        val editName = view.findViewById<EditText>(R.id.editTextEventName)
        val editHost = view.findViewById<EditText>(R.id.editTextHost)
        val editDate = view.findViewById<EditText>(R.id.editTextDate)
        val editTime = view.findViewById<EditText>(R.id.editTextTime)
        val buttonAdd = view.findViewById<ImageButton>(R.id.imageButtonAdd)


        editDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                requireContext(), object :DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                        val setDate= (day.toString() + "-" + (month + 1) + "-" + year)
                        editDate.setText(setDate)
                    }
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        editTime.setOnClickListener{
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)

           val timePickerDialog = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    val setTime = ("$hour:$minute")
                    editTime.setText(setTime)
                }
            },
                hour,
                minute, false
            )
            timePickerDialog.show()
        }


        buttonAdd.setOnClickListener{
            val eventToAdd = Event(name = editName.text.toString(), host = editHost.text.toString(),
            date = editDate.text.toString(),time=editTime.text.toString())
            db.collection("Event").add(eventToAdd)

            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.planEventFrame, eventsListFragment)
            fragmentTransaction?.commit()
        }
        return view
}
}