package moe.xing.eventlist;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
public class EventGroup implements Observable {
    private CharSequence groupTitle;
    private List<Event> events = new ArrayList<>();
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();


    @Bindable
    public CharSequence getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(CharSequence groupTitle) {
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
