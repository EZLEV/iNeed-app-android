package shop.ineed.app.ineed.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import shop.ineed.app.ineed.R;

/**
 * Created by jose on 9/9/17.
 */

public class SlideAdapter extends PagerAdapter {

    private List<Bitmap> mImages;
    private LayoutInflater mInflater;
    private Context mContext;


    public SlideAdapter(Context context, List<Bitmap> images) {
        this.mContext = context;
        this.mImages = images;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.slide_adapter, container, false);

        ImageView imageSlide = (ImageView) view.findViewById(R.id.imageSlide);

        imageSlide.setImageBitmap(mImages.get(position));

        container.addView(view, 0);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}