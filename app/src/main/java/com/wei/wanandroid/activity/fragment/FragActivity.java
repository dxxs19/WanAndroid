package com.wei.wanandroid.activity.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wei.wanandroid.R;
import com.wei.wanandroid.activity.BaseActivity;
import com.wei.wanandroid.widgets.CusViewPager;

import java.util.ArrayList;
import java.util.List;

public class FragActivity extends BaseActivity
{
    public static final int TAB_ONE = 0;
    public static final int TAB_TWO = 1;
    public static final int TAB_THREE = 2;
    private CusViewPager mViewPager;
    private MainTabPagerAdapter mMainTabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
    }

    @Override
    public void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mMainTabPagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainTabPagerAdapter);
        mViewPager.setCurrentItem(TAB_ONE);
        mViewPager.setOffscreenPageLimit(mMainTabPagerAdapter.getCount());
    }

    private class MainTabPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragments;
        private FragmentManager mFragmentManager;

        public MainTabPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
            mFragments = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;
            switch (position)
            {
                case TAB_ONE:
                    fragment = new Fragment1();
                    break;

                case TAB_TWO:
                    fragment = new Fragment2();
                    break;

                case TAB_THREE:
                    fragment = new Fragment3();
                    break;

                    default:
            }
            mFragments.add(fragment);
            return fragment;
        }

        @Override
        public int getCount()
        {
            return 3;
        }
    }
}
