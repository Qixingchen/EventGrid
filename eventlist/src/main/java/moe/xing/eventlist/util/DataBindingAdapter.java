package moe.xing.eventlist.util;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

/**
 * Created by Qi Xingchen on 2018-7-14.
 */
public class DataBindingAdapter {
    /**
     * set view height using data binding
     */
    @BindingAdapter({"layout_height"})
    public static void setHeight(View view, int height) {
        view.getLayoutParams().height = Math.round(height * view.getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * set view height using data binding
     */
    @BindingAdapter({"layout_width"})
    public static void setWidth(View view, int width) {
        view.getLayoutParams().width = Math.round(width * view.getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * set view height using data binding
     */
    @BindingAdapter({"layout_marginTop"})
    public static void setmarginTop(View view, int marginTop) {

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

        marginParams.setMargins(marginParams.leftMargin,
                Math.round(marginTop * view.getContext().getResources().getDisplayMetrics().density),
                marginParams.rightMargin,
                marginParams.bottomMargin);
    }


    /**
     * set view height using data binding
     */
    @BindingAdapter(value = {"backgroundRes", "backgroundColor"}, requireAll = false)
    public static void setBackground(View view, @DrawableRes int backgroundRes, @ColorInt int backgroundColor) {

        if (backgroundRes != 0) {
            view.setBackgroundResource(backgroundRes);
            return;
        }
        view.setBackgroundColor(backgroundColor);


    }
}
