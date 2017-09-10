package shop.ineed.app.ineed.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.gujun.android.taggroup.TagGroup;
import shop.ineed.app.ineed.R;
import shop.ineed.app.ineed.activity.DetailsProductsActivity;
import shop.ineed.app.ineed.adapter.SlideAdapter;
import shop.ineed.app.ineed.domain.Product;
import shop.ineed.app.ineed.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsProductFragment extends Fragment implements ViewPager.OnPageChangeListener {


    private Product mProduct;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    public DetailsProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_product, container, false);
        mProduct = Parcels.unwrap(getArguments().getParcelable("product"));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtTitleProduct = (TextView) view.findViewById(R.id.txtTitleProduct);
        txtTitleProduct.setText(mProduct.getName());
        TextView txtDescriptionProduct = (TextView) view.findViewById(R.id.txtDescription);
        txtDescriptionProduct.setText(mProduct.getDescription());

        TagGroup mTagGroup = (TagGroup) view.findViewById(R.id.tag_group);
        mTagGroup.setTags(mProduct.getCategories());

        initSlide(view);

    }

    private void initSlide(View view) {
        List<Bitmap> images = new ArrayList<>();
        for (int i = 0; i < mProduct.getPictures().size(); i++) {
            images.add(Base64.convertToBitmap(mProduct.getPictures().get(i)));
        }
        mPager = (ViewPager) view.findViewById(R.id.sliderDetailsProductFragment);
        mPager.setAdapter(new SlideAdapter(getActivity(), images));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicatorDetailsSlideFragment);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);


        NUM_PAGES = mProduct.getPictures().size();


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        indicator.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPage = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
