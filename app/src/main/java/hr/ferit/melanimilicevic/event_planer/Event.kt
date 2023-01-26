package hr.ferit.melanimilicevic.event_planer

import android.location.Address
import java.sql.Date
import java.sql.Time

data class Event(
    var id: String="",
    var name: String?=null,
    var host: String?=null,
    var place: String?=null,
    var date: String?=null,
    var time: String?=null,
    var description:String?=null
)
