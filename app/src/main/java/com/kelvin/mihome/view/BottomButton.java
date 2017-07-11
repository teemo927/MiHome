package com.kelvin.mihome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kelvin.mihome.R;

/**
 * Authority: Asus
 * Date: 2017-07-07  9:40
 */

public class BottomButton extends LinearLayout {
    private final int DEFAULT_LAYOUT = R.layout.view_bottom_button;
    private final int SELECTED_COLOR = R.color.tab_icon;
    private final int DEFAULT_COLOR = R.color.tab_text;
    private ProgressBar refresh;
    private ImageView icon;
    private TextView tab;
    private boolean isSelected;
    private boolean isRefreshing;

    public BottomButton(Context context) {
        this(context, null);
    }

    public BottomButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //以下为错误的，导致view不显示
        //View view = inflater.inflate(Default_Layout, this, false);
        View view = inflater.inflate(DEFAULT_LAYOUT, this);

        tab = view.findViewById(R.id.tv_tab);
        icon = view.findViewById(R.id.iv_icon);
        refresh = view.findViewById(R.id.sb_refresh);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomButton);

        CharSequence titleText = a.getText(R.styleable.BottomButton_titleText);
        if (!TextUtils.isEmpty(titleText))
            tab.setText(titleText);

        int titleTextColor = a.getColor(R.styleable.BottomButton_titleTextColor, 0);
        if (titleTextColor != 0)
            tab.setTextColor(titleTextColor);

        float titleTextSize = a.getDimension(R.styleable.BottomButton_titleTextSize, 0);
        if (titleTextSize != 0)
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);

        int imageSrc = a.getResourceId(R.styleable.BottomButton_imageSrc, 0);
        if (imageSrc != 0)
            icon.setImageResource(imageSrc);

        isSelected = a.getBoolean(R.styleable.BottomButton_setTab, false);
        selectedTab(isSelected);

        a.recycle();
    }

    /**
     * 设置当前Tab选中状态
     * @param select 是否选中
     */
    public void setSelected(boolean select) {
        this.isSelected = select;
        selectedTab(this.isSelected);
    }

    public boolean getSelected() {
        return isSelected;
    }

    private void selectedTab(boolean setTab) {
        //icon需要设置一个selector图片
        icon.setSelected(setTab);
        tab.setTextColor(getContext().getResources().getColor(setTab ? SELECTED_COLOR : DEFAULT_COLOR));
    }

    public void stopRefresh() {
        icon.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.GONE);
        setRefreshing(false);
    }

    public void startRefresh() {
        icon.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        setRefreshing(true);
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }
}
