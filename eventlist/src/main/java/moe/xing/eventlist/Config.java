package moe.xing.eventlist;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
public class Config implements Observable {

    private int titleHeight = 40;
    private int hourHeight = 40;
    private int groupWidth = 60;
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();


    @Bindable
    public int getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
        notifyChange(BR.titleHeight);
    }

    @Bindable
    public int getHourHeight() {
        return hourHeight;
    }

    public void setHourHeight(int hourHeight) {
        this.hourHeight = hourHeight;
        notifyChange(BR.hourHeight);
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

    @Bindable
    public int getGroupWidth() {
        return groupWidth;
    }

    public void setGroupWidth(int groupWidth) {
        this.groupWidth = groupWidth;
        notifyChange(BR.groupWidth);
    }
}
