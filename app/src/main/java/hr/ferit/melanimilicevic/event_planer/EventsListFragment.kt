package hr.ferit.melanimilicevic.event_planer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EventsListFragment : Fragment(R.layout.fragment_events_list),
EventRecyclerAdapter.ContentListener {
    private val db=Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events_list, container, false)

        val recyclerView =view.findViewById<RecyclerView>(R.id.recyclerViewEvents)

        db.collection("Event")
            .get()
            .addOnSuccessListener { result ->
                val eventList = ArrayList<Event>()
                for (data in result.documents){
                    val event = data.toObject(Event::class.java)
                    if(event != null){
                        event.id = data.id
                        eventList.add(event)
                    }
                }
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = EventRecyclerAdapter(eventList,this@EventsListFragment)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("EventsListFragment", "Error getting documents.", exception)
            }

        val buttonHome=view.findViewById<Button>(R.id.buttonHome)
        val buttonPlan=view.findViewById<Button>(R.id.buttonEventsList)

        val homeFragment = HomeFragment()
        val planEventFragment = PlanEventFragment()

        buttonHome.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventsListFrame, homeFragment)
            fragmentTransaction?.commit()
        }
        buttonPlan.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventsListFrame, planEventFragment)
            fragmentTransaction?.commit()
        }

        return view
    }

    override fun onItemButtonCLick(index: Int, event: Event, clickType: ItemClickType) {
        if (clickType == ItemClickType.EDIT) {
            db.collection("Event")
                .document(event.id)
                .set(event)
        }
    }

    /*override fun onItemButtonCLick(index: Int, event: Event, clickType: ItemClickType) {
        if (clickType == ItemClickType.EDIT) {
            db.collection("Event")
                .document(event.id)
                .set(event)
        }
        else if (clickType == ItemClickType.REMOVE) {
            EventRecyclerAdapter(removeItem(index)
            db.collection("Event")
                .document(event.id)
                .delete()
        }
    }*/
}


