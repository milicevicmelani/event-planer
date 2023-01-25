package hr.ferit.melanimilicevic.event_planer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

enum class  ItemClickType{
    EDIT,
    REMOVE
}


class EventRecyclerAdapter (
    val items:ArrayList<Event>,
    val listener: ContentListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.event_recycler_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder
                                  , position: Int)
        { when(holder) {
            is EventViewHolder -> {
                holder.bind(position,listener,items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, items.size)
    }


    class EventViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val eventName: TextView =
            itemView.findViewById(R.id.EventName)
        private val eventHost: TextView =
            itemView.findViewById(R.id.EventHost)
        private val eventDateTime: TextView =
            itemView.findViewById(R.id.EventDateTime)
        private  val deleteButton : ImageButton =
            itemView.findViewById(R.id.imageButtonDelete)
        private val editButton : ImageButton =
            itemView.findViewById(R.id.imageButtonEdit)

        fun bind(
            index: Int,
            listener: ContentListener,
            event: Event
        ) {
            eventName.text = event.name
            eventHost.text = event.host
            eventDateTime.text = (event.date + ", " + event.time)


            editButton.setOnClickListener{
                listener.onItemButtonCLick(index,event,ItemClickType.EDIT)
            }

            deleteButton.setOnClickListener {
                listener.onItemButtonCLick(index,event,ItemClickType.REMOVE )
            }
        }
    }

    interface  ContentListener{
        fun onItemButtonCLick(index: Int, event: Event,clickType: ItemClickType)
    }
}