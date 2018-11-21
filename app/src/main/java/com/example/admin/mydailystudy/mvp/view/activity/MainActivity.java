package com.example.admin.mydailystudy.mvp.view.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.mvp.present.CollectArticleDataPresent;
import com.example.admin.mydailystudy.mvp.present.LoginAndRegisterPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.CollectArticleDataPresentImpl;
import com.example.admin.mydailystudy.mvp.presentIpml.LoginAndRegisterPresentImpl;
import com.example.admin.mydailystudy.mvp.view.fragment.MyDialogFragment;
import com.example.admin.mydailystudy.mvp.view.fragment.ProjectFragment;
import com.example.admin.mydailystudy.mvp.view.fragment.StudyFragment;
import com.example.admin.mydailystudy.mvp.view.fragment.SystemFragment;
import com.example.admin.mydailystudy.mvp.view.view.CollectArticleDataView;
import com.example.admin.mydailystudy.mvp.view.widget.MyViewPager;
import com.example.admin.mydailystudy.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.viewPager)
    MyViewPager viewPager;
    @Bind(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    private RelativeLayout headerView;
    private MenuItem bottomItem;

    private List<Fragment> fragments;
    private MyViewPagerAdapter pagerAdapter;
    private MenuItem logoutMenuItem;
    private TextView tv_username;

    private CollectArticleDataPresent present;

    LoginAndRegisterPresent present1;

    @Override
    public int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        //得到头布局
        headerView = (RelativeLayout) navigationView.getHeaderView(0);
        tv_username = headerView.findViewById(R.id.tv_username);
        logoutMenuItem = navigationView.getMenu().findItem(R.id.logout);
        fragments = new ArrayList<>();
        fragments.add(new StudyFragment());
        fragments.add(new SystemFragment());
        fragments.add(new ProjectFragment());
        pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        initListener();
//        present = new CollectArticleDataPresentImpl(this);
        present1 = new LoginAndRegisterPresentImpl(new LoginActivity());
        if (!MyConstants.isLogin) {
            checkAutoLogin();
        }
//        bottom_tab_bar.init(getSupportFragmentManager(), 720, 1280)
////                .setImgSize(70, 70)
////                .setFontSize(14)
////                .setTabPadding(5, 0, 5)
////                .setChangeColor(Color.parseColor("#FF00F0"),Color.parseColor("#CCCCCC"))
//                .addTabItem("学习", R.drawable.ic_study_selected, R.drawable.ic_study_normal, StudyFragment.class)
//                .addTabItem("娱乐", R.drawable.ic_recreation_selected, R.drawable.ic_recreation_normal, RecreationFragment.class)
//                .addTabItem("生活", R.drawable.ic_life_selected, R.drawable.ic_life_normal, LifeFragment.class)
////                .isShowDivider(true)
////                .setDividerColor(Color.parseColor("#FF0000"))
////                .setTabBarBackgroundColor(Color.parseColor("#00FF0000"))
//                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
//                    @Override
//                    public void onTabChange(int position, String name, View view) {
//                        if (position == 0) {
//                            viewPager.setCurrentItem(0);
//                            bottom_tab_bar.setSpot(0, true);
//                            bottom_tab_bar.setSpot(1, false);
//                            bottom_tab_bar.setSpot(2, false);
//
//
//                        } else if ((position == 1)) {
//                            viewPager.setCurrentItem(1);
//                            bottom_tab_bar.setSpot(1, true);
//                            bottom_tab_bar.setSpot(0, false);
//                            bottom_tab_bar.setSpot(2, false);
//                        } else {
//                            viewPager.setCurrentItem(2);
//                            bottom_tab_bar.setSpot(2, true);
//                            bottom_tab_bar.setSpot(0, false);
//                            bottom_tab_bar.setSpot(1, false);
//                        }
//                    }
//                })
//                .setSpot(0, true)
//                .setSpot(1, false)
//                .setSpot(2, false);

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    private void initListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.my_favorite:
                        if (MyConstants.isLogin) {
                            startActivity(new Intent(MainActivity.this, MyCollectedActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "您还没有登录！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                    case R.id.weather:
                        if (MyConstants.isLogin) {
                            startActivity(new Intent(MainActivity.this, MyTodoActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "您还没有登录！", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                        break;
                    case R.id.change_skin:
                        MyDialogFragment dialogFragment = new MyDialogFragment();
                        dialogFragment.show(getSupportFragmentManager(), "dialogFragment");
                        break;
                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    case R.id.logout:
                        present1.userLogout();
                        logoutMenuItem.setVisible(false);
//                        present.getCollectArticle(0);

                        break;
                    default:
                        break;
                }
                //设置选中
//                if (lastItem != null) {
//                    lastItem.setChecked(false);
//                }
//                menuItem.setChecked(true);
                drawer_layout.closeDrawers();
                return false;
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_study:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.bottom_recreation:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.bottom_life:
                        viewPager.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
                //设置选中
                if (bottomItem != null) {
                    bottomItem.setChecked(false);
                }
                menuItem.setChecked(true);
                bottomItem = menuItem;
                return false;
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Message msg) {
        if (msg.what == BaseFragment.NAVIGATION_HIDE) {
            hideBottomNav(bottomNavigationView);
        } else if (msg.what == BaseFragment.NAVIGATION_SHOW) {
            showBottomNav(bottomNavigationView);
        } else if (msg.what == 2) {
            logoutMenuItem.setVisible(true);
            tv_username.setText((String) SharedPreferenceUtil.getData(this, "user_name", ""));
        }
    }

    private void checkAutoLogin() {
        boolean rememberPwd = (boolean) SharedPreferenceUtil.getData(this, "remember_pwd", false);
        if (rememberPwd) {
            String name = (String) SharedPreferenceUtil.getData(this, "user_name", "");
            String pwd = (String) SharedPreferenceUtil.getData(this, "user_pwd", "");
            present1.userLogin(name, pwd, true);
            logoutMenuItem.setVisible(true);
            tv_username.setText(name);
        }
    }


    //显示底部导航栏
    private void showBottomNav(final View mTarget) {
        // 这种效果最好
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getTop());//
        va.setDuration(800);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                /**
                 * 通过这样一个监听事件，我们就可以获取
                 * 到ValueAnimator每一步所产生的值。
                 *
                 * 通过调用getAnimatedValue()获取到每个时间因子所产生的Value。
                 * */
                mTarget.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    //隐藏底部导航栏
    private void hideBottomNav(final View mTarget) {
        //这种效果最好
        ValueAnimator va = ValueAnimator.ofFloat(mTarget.getY(), mTarget.getBottom());
        va.setDuration(800);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTarget.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
    }

    //打开侧拉菜单
    public void openDrawer() {
        drawer_layout.openDrawer(Gravity.START);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        if (bottomItem != null) {
            bottomItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        bottomItem = bottomNavigationView.getMenu().getItem(position);
        bottomItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


    }

    private long mExitTime;

    /**
     * 用户点击返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {

                drawer_layout.closeDrawers();

                return true;
            }

            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出程序！", Toast.LENGTH_SHORT).show();

                mExitTime = System.currentTimeMillis();
            } else {

                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
