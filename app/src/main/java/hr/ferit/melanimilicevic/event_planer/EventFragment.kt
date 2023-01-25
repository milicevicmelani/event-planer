package hr.ferit.melanimilicevic.event_planer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction


class EventFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        val buttonHome=view.findViewById<Button>(R.id.buttonHome)
        val buttonEvents=view.findViewById<Button>(R.id.buttonEventsList)

        val homeFragment = HomeFragment()
        val eventsListFragment = EventsListFragment()


        buttonHome.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventFrame, homeFragment)
            fragmentTransaction?.commit()
        }

        buttonEvents.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.eventFrame, eventsListFragment)
            fragmentTransaction?.commit()
        }




        return view
    }


}