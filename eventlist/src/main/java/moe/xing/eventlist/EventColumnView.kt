package moe.xing.eventlist

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import moe.xing.eventlist.databinding.ItemEventBinding
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
open class EventColumnView(context: Context) : FrameLayout(context) {
    private val density = context.resources.displayMetrics.density

    @Suppress("MemberVisibilityCanBePrivate")
    var onEventClickListener: ((Event) -> Unit)? = null

    @Suppress("MemberVisibilityCanBePrivate")
    var widthDp = EventView.config.groupWidth
        set(value) {
            layoutParams = LayoutParams(Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics)), LayoutParams.MATCH_PARENT)
            requestLayout()
            field = value
        }

    init {
        widthDp = EventView.config.groupWidth
    }

    fun setList(events: List<Event>) {
        removeAllViews()
        val inflater = LayoutInflater.from(context)
        events.sortedBy { it.start }.forEach { event ->
            val binding = DataBindingUtil.inflate<ItemEventBinding>(inflater, R.layout.item_event, null, false)
            binding.event = event
            binding.root.setOnClickListener {
                onEventClickListener?.invoke(event)
            }

            // find same time event
            val sameTimeList = events.sortedBy { it.start }.filter { eventToCompare -> event.start.time.coerceAtLeast(eventToCompare.start.time) < event.end.time.coerceAtMost(eventToCompare.end.time) }

            if (sameTimeList.isEmpty()) {
                return@forEach
            }

            val (fromY, y) = run {
                val startParams = getParams(event.start)
                val endParams = getParams(event.end)
                startParams.fromY to endParams.fromY - startParams.fromY
            }

            val width = ((widthDp / sameTimeList.size - 1) * density).toInt()
            val marginParams = LayoutParams(width, (y * density).toInt()).apply {
                topMargin = (fromY * density).toInt()
                leftMargin = (width + 1) * sameTimeList.indexOf(event) + (TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 1.toFloat(), context.resources.displayMetrics)).toInt() * (sameTimeList.indexOf(event) + 1)
            }
            addView(binding.root, LayoutParams(marginParams))
        }


    }

    private fun getParams(date: Date, addDays: Int = 0): TimeParams {
        val cal = Calendar.getInstance().apply { time = date }
        return TimeParams(cal[Calendar.HOUR_OF_DAY] + (addDays * 24), cal[Calendar.MINUTE])
    }
}

data class TimeParams(
        val hour: Int,
        val min: Int
) {
    companion object {
        fun from(y: Float, density: Float): TimeParams {
            val roundY = (y * 10 / density).roundToInt() / 10
            return TimeParams(roundY / EventView.config.hourHeight, (roundY % EventView.config.hourHeight) * EventView.config.hourHeight / 60)
        }
    }

    val fromY = ((hour * EventView.config.hourHeight) + (min * EventView.config.hourHeight / 60))
}