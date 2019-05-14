package moe.xing.eventlist;

import android.graphics.Color;

import java.util.Date;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

/**
 * Created by Qi Xingchen on 2018-7-17.
 */
public class Event implements Observable {

    /**
     * 事件标题
     */
    private CharSequence title;
    /**
     * 事件开始时间(只判断时间)
     */
    private Date start;
    /**
     * 事件结束时间(只判断时间)
     */
    private Date end;
    /**
     * 事件背景资源(较 #backGroundColor 更加优先)
     */
    @DrawableRes
    private int backgroundRes;
    /**
     * 事件背景颜色(#backGroundRes 提供时无效)
     */
    @ColorInt
    private int backgroundColor;
    /**
     * 事件文字颜色
     */
    @ColorInt
    private int textColor = Color.argb(255, 102, 204, 255);
    private transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();


    @Bindable
    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
        notifyChange(BR.title);
    }

    @Bindable
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
        notifyChange(BR.start);
    }

    @Bindable
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
        notifyChange(BR.end);
    }

    @Bindable
    public int getBackgroundRes() {
        return backgroundRes;
    }

    public void setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
        notifyChange(BR.backgroundRes);
    }

    @Bindable
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        notifyChange(BR.backgroundColor);
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
    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        notifyChange(BR.textColor);
    }
}
