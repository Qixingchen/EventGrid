package moe.xing.eventlist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moe.xing.eventlist.databinding.ItemTitleBinding
import moe.xing.rvutils.BaseRecyclerViewAdapter

/**
 * Created by Qi Xingchen on 2018-7-16.
 */
open class TitleAdapter : BaseRecyclerViewAdapter<CharSequence, ViewHolder>(CharSequence::class.java) {


    fun widthChange() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTitleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_title, null, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindVH(datas.get(position) ?: SpannableString(""))
    }

    fun replace(newTitles: List<CharSequence>) {
        datas.clear()
        datas.addAll(newTitles)
        notifyDataSetChanged()
    }

    override fun rvAreItemsTheSame(item1: CharSequence?, item2: CharSequence?): Boolean {
        return false
    }
}

open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mBinding: ItemTitleBinding? = null

    init {
        mBinding = DataBindingUtil.findBinding(itemView)
    }

    fun bindVH(title: CharSequence) {
        mBinding?.title = title
        mBinding?.config = EventView.config
    }
}

