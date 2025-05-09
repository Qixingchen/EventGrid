package moe.xing.eventlistdemo

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import moe.xing.baseutils.Init
import moe.xing.baseutils.utils.DateUtils
import moe.xing.eventlist.Event
import moe.xing.eventlist.EventGroup
import moe.xing.eventlist.EventView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Init.init(application, true, "1.0", "event list")

        val eventView: EventView = findViewById(R.id.event)

        val event1 = Event().apply {
            setTitle("test1")
            backgroundColor = Color.argb(255, 120, 145, 154)
            start = DateUtils.parse("6:15", "HH:mm")
            end = DateUtils.parse("9:00", "HH:mm")

        }

        val event2 = Event().apply {
            setTitle("test2")
            backgroundColor = Color.argb(255, 45, 145, 154)
            start = DateUtils.parse("11:45", "HH:mm")
            end = DateUtils.parse("13:45", "HH:mm")

        }

        val event3 = Event().apply {
            setTitle("test3")
            backgroundColor = Color.argb(255, 141, 145, 154)
            start = DateUtils.parse("11:45", "HH:mm")
            end = DateUtils.parse("13:45", "HH:mm")
            isShowTopLine = true
        }

        val event4 = Event().apply {
            setTitle("test4")
            backgroundColor = Color.argb(80, 120, 0, 0)
            start = DateUtils.parse("11:45", "HH:mm")
            end = DateUtils.parse("13:45", "HH:mm")

        }

        val group1 = EventGroup().apply {
            setGroupTitle("group1")
            events.add(event1)
            events.add(event2)
            events.add(event3)
        }

        val group2 = EventGroup().apply {
            setGroupTitle("group2")
            events.add(event1)
            events.add(event2)
            events.add(event3)
            events.add(event4)
        }

        val group3 = EventGroup().apply {
            setGroupTitle("group3")
            events.add(event4)
            events.add(event2)
            events.add(event1)
        }

        val groups = listOf(group1, group2, group3, EventGroup(), EventGroup(), EventGroup(), EventGroup(), EventGroup(), EventGroup())

        eventView.replace(groups)
        eventView.scrollToHour(9)
        eventView.getEventRecyclerView().addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(this, androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL))
        eventView.getTitleRecyclerView().addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(this, androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL))
        EventView.config.hourHeight = 60
        EventView.config.titleHeight = 60
        EventView.config.groupWidth = 90

        eventView.setClickListener {
            Toast.makeText(this, it.title, Toast.LENGTH_LONG).show()
        }

    }
}
