package com.unimi.unimiapp.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.unimi.unimiapp.R;

import java.util.ArrayList;
import java.util.List;

public class CustomImageSlider extends FrameLayout {

    private ViewPager viewPager;
    private CustomPagerAdapter pagerAdapter;

    public CustomImageSlider(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomImageSlider(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageSlider(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_image_slider, this, true);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new CustomPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
    }

    public void setImages(List<Integer> imageResIds) {
        pagerAdapter.setImages(imageResIds);
        pagerAdapter.notifyDataSetChanged();
    }

    private static class CustomPagerAdapter extends PagerAdapter {

        private List<Integer> images = new ArrayList<>();

        public void setImages(List<Integer> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull View container, int position) {
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_item, null);
            ImageView imageView = itemView.findViewById(R.id.image);
            imageView.setImageResource(images.get(position));
            ((ViewPager) container).addView(itemView);
            return itemView;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
