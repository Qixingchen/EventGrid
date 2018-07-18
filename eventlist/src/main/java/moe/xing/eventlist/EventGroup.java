package moe.xing.eventlist;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.text.SpannableString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
public class EventGroup implements Observable {
    private SpannableString groupTitle;
    private List<Event> events = new ArrayList<>();
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();


    @Bindable
    public SpannableString getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = new SpannableString(groupTitle);
        notifyChange(BR.groupTitle);
    }

    public void setGroupTitle(SpannableString groupTitle) {
        this.groupTitle = groupTitle;
        notifyChange(BR.groupTitle);
    }

    @Bindable
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyChange(BR.events);
    }

    private synchronized void notifyChange(int propertyId) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.notifyChange(this, propertyId);
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry == null) {
            propertyChangeRegistry = new PropertyChangeRegistry();
        }
        propertyChangeRegistry.add(callback);

    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        if (propertyChangeRegistry != null) {
            propertyChangeRegistry.remove(callback);
        }
    }
}
