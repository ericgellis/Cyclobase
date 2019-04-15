package com.mobithink.velo.carbon.starter.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mobithink.velo.carbon.R;
import com.mobithink.velo.carbon.core.ui.AbstractActivity;
import com.mobithink.velo.carbon.home.ui.HomeActivity;
import com.mobithink.velo.carbon.splashscreen.ui.SplashScreenActivity;
import com.mobithink.velo.carbon.starter.callback.StarterCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarterActivity extends AbstractActivity implements StarterCallback {

    public static final String EMAIL_CONFIRMED = "EMAIL_CONFIRMED";

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.starter_activity);
        ButterKnife.bind(this);
        setDarkStatusIcon();

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignInFragment().setCallBack(this), getString(R.string.signin));
        adapter.addFragment(new SignUpFragment(), getString(R.string.signup));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onAuthCompleted() {
        startActivity(new Intent(this, HomeActivity.class));
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

}
