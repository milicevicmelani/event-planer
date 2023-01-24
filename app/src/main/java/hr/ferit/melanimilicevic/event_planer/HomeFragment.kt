package hr.ferit.melanimilicevic.event_planer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val buttonEvents=view.findViewById<Button>(R.id.buttonEvents)
        val buttonPlan=view.findViewById<Button>(R.id.buttonEventsList)

        val eventsListFragment = EventsListFragment()
        val planEventFragment = PlanEventFragment()

        buttonEvents.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.homeFrame, eventsListFragment)
            fragmentTransaction?.commit()
        }
        buttonPlan.setOnClickListener{
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.homeFrame, planEventFragment)
            fragmentTransaction?.commit()
        }

        return view
    }

}