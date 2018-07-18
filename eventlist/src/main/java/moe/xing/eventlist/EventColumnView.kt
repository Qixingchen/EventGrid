package moe.xing.eventlist

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.widget.FrameLayout
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
            layoutParams = FrameLayout.LayoutParams((value * density).toInt(), FrameLayout.LayoutParams.MATCH_PARENT).apply {
                this.setMargins(density.toInt(), 0, density.toInt(), 0)
            }
            requestLayout()
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

            val (fromY, y) = run {
                val startParams = getParams(event.start)
                val endParams = getParams(event.end)
                startParams.fromY to endParams.fromY - startParams.fromY
            }

            val marginParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (y * density).toInt()).apply {
                topMargin = (fromY * density).toInt()
            }
            addView(binding.root, FrameLayout.LayoutParams(marginParams))
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
            val roundY = (y * 10 / density).roundToInt() / 10 - 6
            return TimeParams(roundY / EventView.config.hourHeight, (roundY % EventView.config.hourHeight) * EventView.config.hourHeight / 60)
        }
    }

    val fromY = ((hour * EventView.config.hourHeight) + (min * EventView.config.hourHeight / 60)) + 6
}