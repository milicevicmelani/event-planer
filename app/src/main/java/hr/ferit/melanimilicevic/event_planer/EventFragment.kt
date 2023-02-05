package hr.ferit.melanimilicevic.event_planer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class EventFragment : Fragment() {
    private val db= Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        val buttonPlan=view.findViewById<Button>(R.id.buttonPlanEvent)
        val buttonEvents=view.findViewById<Button>(R.id.buttonEvent)

        val planFragment = PlanEventFragment()
        val eventsListFragment = EventsListFragment()

        buttonPlan.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventFrame, planFragment)
            fragmentTransaction?.commit()
        }

        buttonEvents.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventFrame, eventsListFragment)
            fragmentTransaction?.commit()
        }

        val eventID=arguments?.getString("eventID").toString()

        val textName=view.findViewById<TextView>(R.id.textViewEventTitle)
        val textHost=view.findViewById<TextView>(R.id.textViewHost)
        val textDate=view.findViewById<TextView>(R.id.textViewDate)
        val textTime=view.findViewById<TextView>(R.id.textViewTime)
        val textPlace=view.findViewById<TextView>(R.id.textViewAddress)


        db.collection("Event")
            .document(eventID)
            .get()
            .addOnSuccessListener { document ->
                val event = document.toObject(Event::class.java)
                if (document != null) {
                    textName.text=event?.name
                    textHost.text=event?.host
                    textDate.text=event?.date
                    textTime.text=event?.time
                    textPlace.text=event?.place

                }
            }
            .addOnFailureListener { exception ->
                Log.w("EventsListFragment", "Error getting documents.", exception)
            }



        return view
    }
}