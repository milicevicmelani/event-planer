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
    private lateinit var adapter: EventRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events_list, container, false)

        val recyclerView =view.findViewById<RecyclerView>(R.id.recyclerViewEvents)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EventRecyclerAdapter(java.util.ArrayList(),this@EventsListFragment)
        }

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
                adapter=EventRecyclerAdapter(eventList,this@EventsListFragment)
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
        val eventFragment = EventFragment()
        val bundle = Bundle()
        bundle.putString("eventID", event.id)
        eventFragment.arguments = bundle

        eventFragment.arguments = bundle
        if (clickType == ItemClickType.MORE) {
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventsListFrame, eventFragment)
            fragmentTransaction?.commit()
        }
        else if (clickType == ItemClickType.REMOVE) {
            adapter.removeItem(index)
            event.id?.let {
                db.collection("Event")
                    .document(it)
                    .delete()
            }
        }
    }
}


