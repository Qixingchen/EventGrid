package moe.xing.eventlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import moe.xing.rvutils.BaseRecyclerViewAdapter;

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
public class EventAdapter extends BaseRecyclerViewAdapter<EventGroup, EventAdapter.ViewHolder> {
    EventAdapter() {
        super(EventGroup.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new EventColumnView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bindVH(getItem(position));
    }

    public void replace(@NonNull List<EventGroup> eventGroups) {
        removeAllData();
        addData(eventGroups);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private EventColumnView view;

        ViewHolder(EventColumnView itemView) {
            super(itemView);
            view = itemView;
        }

        void bindVH(@Nullable final EventGroup eventGroup) {
            if (eventGroup != null) {
                view.setList(eventGroup.getEvents());
            }
        }
    }
}
