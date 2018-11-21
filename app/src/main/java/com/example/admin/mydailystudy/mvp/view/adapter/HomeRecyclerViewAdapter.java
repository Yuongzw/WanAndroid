package com.example.admin.mydailystudy.mvp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.HomeBannerDataBean;
import com.example.admin.mydailystudy.bean.HomeDataBean;
import com.example.admin.mydailystudy.event.ColletedEvent;
import com.example.admin.mydailystudy.mvp.view.activity.LoginActivity;
import com.example.admin.mydailystudy.mvp.view.activity.MainActivity;
import com.example.admin.mydailystudy.mvp.view.activity.MyTodoActivity;
import com.example.admin.mydailystudy.utils.ItemAnimHelper;
import com.example.admin.mydailystudy.utils.Util;
import com.jaren.lib.view.LikeView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    /**
     * Item类型,int值.必须从0开始依次递增.
     */
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CONTENT = 1;

    /**
     * Item Type 的数量
     */
    private static final int TYPE_ITEM_COUNT = 2;

    private List<HomeDataBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    private List<HomeBannerDataBean.DataBean> bannerBeans = new ArrayList<>();
    private Context context;

    private ContentViewHolder contentViewHolder;
    private HeadViewHolder headViewHolder;

    private MyPagerAdapter pagerAdapter;
    private int preposition;

    private MyHandler mHandler;

    private ItemAnimHelper helper = new ItemAnimHelper();

    private int[] array;

    private int[] imageResources = new int[]{
            R.drawable.squirrel,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,
            R.drawable.cat,
            R.drawable.deer,
            R.drawable.dolphin,
            R.drawable.eagle,
            R.drawable.horse,
            R.drawable.elephant,
            R.drawable.owl,
            R.drawable.peacock,
            R.drawable.pig,
            R.drawable.rat,
            R.drawable.snake,
            R.drawable.bat
    };

    public HomeRecyclerViewAdapter(Context context) {
        this.context = context;
        array = new int[]{
                context.getResources().getColor(R.color.cardView),
                context.getResources().getColor(R.color.cardView1),
                context.getResources().getColor(R.color.cardView2),
                context.getResources().getColor(R.color.colorAccent),
                context.getResources().getColor(R.color.xxblue)};
    }

    public void addBanner(List<HomeBannerDataBean.DataBean> bannerBeans) {
        this.bannerBeans.clear();
        this.bannerBeans.addAll(bannerBeans);

    }

    public void addHomeInfo(List<HomeDataBean.DataBean.DatasBean> datas, boolean clear) {
        if (clear) {
            datasBeans.clear();
        }
        datasBeans.addAll(datas);

//        notifyItemRangeInserted(datas.size(),20);    //局部插入

        notifyItemRangeChanged(datasBeans.size() == 20 ? 1 : datasBeans.size(), 21); //如果集合数据是20，代表下拉刷新，则刷新前20条
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                View head = LayoutInflater.from(context).inflate(R.layout.rv_head_item, viewGroup, false);
                headViewHolder = new HeadViewHolder(head);
                return headViewHolder;
            case TYPE_CONTENT:
                View content = LayoutInflater.from(context).inflate(R.layout.rv_normal_item, viewGroup, false);
                contentViewHolder = new ContentViewHolder(content);
                return contentViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_TITLE) {
            if (bannerBeans != null && bannerBeans.size() > 0) {
                if (pagerAdapter == null) {
                    pagerAdapter = new MyPagerAdapter();
                    ((HeadViewHolder) viewHolder).vp_banner.setAdapter(pagerAdapter);
                    addRedPoint();
                } else {
                    pagerAdapter.notifyDataSetChanged();
                }

            }
            if (mHandler == null) {
                mHandler = new MyHandler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(0);
                    }
                }, 3000);
            }

        } else {
            setContentHolder(viewHolder, position);
            viewHolder.itemView.setTag(position - 1);
        }
    }

    private void setContentHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (datasBeans != null && datasBeans.size() > 0) {
            ((ContentViewHolder) viewHolder).tv_normal_author.setText(datasBeans.get(position - 1).getAuthor());
            ((ContentViewHolder) viewHolder).tv_normal_date.setText(datasBeans.get(position - 1).getNiceDate());
            ((ContentViewHolder) viewHolder).tv_normal_title.setText(datasBeans.get(position - 1).getTitle());
            ((ContentViewHolder) viewHolder).tv_normal_type.setText(datasBeans.get(position - 1).getChapterName());
            helper.showItemAnim(((ContentViewHolder) viewHolder).itemView, position - 1);
            Random random = new Random();
            Random random1 = new Random();
            int i = random.nextInt(array.length);
            int j = random1.nextInt(imageResources.length);
            ((ContentViewHolder) viewHolder).cardView.setCardBackgroundColor(array[i]);
            ((ContentViewHolder) viewHolder).imageView.setImageResource(imageResources[j]);
            ((ContentViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FinestWebView.Builder(context).show(datasBeans.get(position - 1).getLink());
                }
            });
            ((ContentViewHolder) viewHolder).likeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MyConstants.isLogin) {
                        if (((ContentViewHolder) viewHolder).likeView.isChecked()) {
                            ((ContentViewHolder) viewHolder).likeView.setChecked(false);
                            EventBus.getDefault().post(new ColletedEvent(datasBeans.get(position - 1).getId(), false));
                        } else {
                            ((ContentViewHolder) viewHolder).likeView.setChecked(true);
                            EventBus.getDefault().post(new ColletedEvent(datasBeans.get(position - 1).getId(), true));
                        }
                    } else {
                        Toast.makeText(context, "您还没有登录！", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (bannerBeans.size() > 0 && datasBeans.size() > 0) {
            return datasBeans.size() + 1;
        } else if (bannerBeans.size() > 0 && datasBeans.size() == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class HeadViewHolder extends RecyclerView.ViewHolder {

        private ViewPager vp_banner;
        private TextView tv_desc;
        private LinearLayout ll_point;


        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            vp_banner = itemView.findViewById(R.id.vp_banner);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            ll_point = itemView.findViewById(R.id.ll_point);

            vp_banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    ll_point.getChildAt(preposition).setEnabled(false);
                    ll_point.getChildAt(position).setEnabled(true);
                    preposition = position;
                    headViewHolder.tv_desc.setText(bannerBeans.get(position).getTitle());
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_normal_author;
        private TextView tv_normal_date;
        private TextView tv_normal_title;
        private TextView tv_normal_type;
        private CardView cardView;
        private LikeView likeView;
        private ImageView imageView;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_normal_author = itemView.findViewById(R.id.tv_normal_author);
            tv_normal_date = itemView.findViewById(R.id.tv_normal_date);
            tv_normal_title = itemView.findViewById(R.id.tv_normal_title);
            tv_normal_type = itemView.findViewById(R.id.tv_normal_type);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            likeView = itemView.findViewById(R.id.lv);

        }
    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //切换到下一个页面
            int item = (headViewHolder.vp_banner.getCurrentItem() + 1) % bannerBeans.size();
            headViewHolder.vp_banner.setCurrentItem(item);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0);
                }
            }, 3000);
        }
    }

    private void addRedPoint() {
        headViewHolder.ll_point.removeAllViews();//移除所有的红点
        LinearLayout.LayoutParams parems = new LinearLayout.LayoutParams(Util.dip2px(8, context), Util.dip2px(8, context));
        if (bannerBeans != null && bannerBeans.size() > 0) {
            for (int i = 0; i < bannerBeans.size(); i++) {
                //创建红点
                ImageView imageView = new ImageView(context);
                imageView.setBackgroundResource(R.drawable.point_selector);
                if (i == 0) {
                    imageView.setEnabled(true);
                } else {
                    imageView.setEnabled(false);
                    parems.leftMargin = Util.dip2px(8, context);
                }
                imageView.setLayoutParams(parems);
                headViewHolder.ll_point.addView(imageView);
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bannerBeans.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @SuppressLint("ClickableViewAccessibility")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
//            imageView.setBackgroundResource(R.drawable.me);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            imageView.setClickable(true);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mHandler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mHandler.sendEmptyMessage(0);
                                }
                            }, 2000);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mHandler.sendEmptyMessage(0);
                                }
                            }, 2000);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FinestWebView.Builder(context).show(bannerBeans.get(position).getUrl());
                }
            });

            Glide.with(context)
                    .load(bannerBeans.get(position).getImagePath())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
