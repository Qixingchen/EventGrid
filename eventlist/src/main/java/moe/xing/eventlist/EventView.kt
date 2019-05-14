package moe.xing.eventlist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moe.xing.eventlist.databinding.EventListViewBinding

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
class EventView : FrameLayout {

    private val eventAdapter = EventAdapter()
    private val titleAdapter = TitleAdapter()

    var binding: EventListViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.event_list_view, this, true)

    companion object {

        val config = Config()
    }

    fun replace(eventGroups: List<EventGroup>) {
        eventAdapter.replace(eventGroups)

        titleAdapter.replace(eventGroups.map { it.groupTitle }.toList())
    }

    fun scrollToHour(hour: Int) {
        binding.scrollView.post {
            binding.scrollView.scrollY = ((hour * config.hourHeight - 4) * context.resources.displayMetrics.density).toInt()
        }
    }

    fun setClickListener(eventListener: ((Event) -> Unit)) {
        eventAdapter.eventListener = eventListener
    }

    fun getEventRecyclerView(): RecyclerView = binding.eventGridRecyclerView
    fun getTitleRecyclerView(): RecyclerView = binding.groupTitle

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        binding.config = config
        binding.eventGridRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.eventGridRecyclerView.adapter = eventAdapter

        binding.groupTitle.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.groupTitle.adapter = titleAdapter

        val scrollListeners = arrayOfNulls<RecyclerView.OnScrollListener>(2)
        scrollListeners[0] = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.eventGridRecyclerView.removeOnScrollListener(scrollListeners[1]!!)
                binding.eventGridRecyclerView.scrollBy(recyclerView.computeHorizontalScrollOffset() - binding.eventGridRecyclerView.computeHorizontalScrollOffset(), 0)
                binding.eventGridRecyclerView.addOnScrollListener(scrollListeners[1]!!)
            }
        }
        scrollListeners[1] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.groupTitle.removeOnScrollListener(scrollListeners[0]!!)
                binding.groupTitle.scrollBy(recyclerView.computeHorizontalScrollOffset() - binding.groupTitle.computeHorizontalScrollOffset(), 0)
                binding.groupTitle.addOnScrollListener(scrollListeners[0]!!)
            }
        }
        binding.groupTitle.addOnScrollListener(scrollListeners[0]!!)
        binding.eventGridRecyclerView.addOnScrollListener(scrollListeners[1]!!)
    }
}