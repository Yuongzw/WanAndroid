package com.example.admin.mydailystudy.mvp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.SkinColor;
import com.example.admin.mydailystudy.mvp.view.adapter.MyRvDialogAdapter;
import com.example.admin.mydailystudy.utils.SharedPreferenceUtil;
import com.zhy.changeskin.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    private RecyclerView rv_dialog;

    private TextView tv_cancel, tv_confirm, tv_restore;

    private List<SkinColor> skinColors = new ArrayList<>();

    private String Theme_Color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置style
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogAnimation);
        setStyle(STYLE_NO_TITLE, getTheme());
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置 dialog 的宽高
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置 dialog 的背景为 null
        getDialog().getWindow().setBackgroundDrawable(null);

        getDialog().setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        return createView(inflater, container);
    }

    private View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.dialog_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_restore = view.findViewById(R.id.tv_restore);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_restore.setOnClickListener(this);
        skinColors.add(new SkinColor(R.color.main_bg_base, "base"));
        skinColors.add(new SkinColor(R.color.main_bg_blue, "blue"));
        skinColors.add(new SkinColor(R.color.main_bg_light, "light"));
        skinColors.add(new SkinColor(R.color.main_bg_orange, "orange"));
        skinColors.add(new SkinColor(R.color.main_bg_teal, "teal"));
        skinColors.add(new SkinColor(R.color.main_bg_brown, "brown"));
        skinColors.add(new SkinColor(R.color.main_bg_green, "green"));
        skinColors.add(new SkinColor(R.color.main_bg_red, "red"));
        rv_dialog = view.findViewById(R.id.rv_dialog);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 4);
        rv_dialog.setLayoutManager(gridLayoutManager);
        final MyRvDialogAdapter adapter = new MyRvDialogAdapter(getActivity(), skinColors);
        rv_dialog.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRvDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                for (int i = 0; i < skinColors.size(); i++) {
                    skinColors.get(i).setChoosed(false);
                }
                skinColors.get(position).setChoosed(true);
                SkinManager.getInstance().changeSkin(skinColors.get(position).getColorName());
                Theme_Color = skinColors.get(position).getColorName();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.BOTTOM; //底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.onWindowAttributesChanged(lp);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.gravity = Gravity.BOTTOM; //底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        dialog.onWindowAttributesChanged(lp);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                SkinManager.getInstance().removeAnySkin();
                String theme_color = (String) SharedPreferenceUtil.getData(getActivity(), "Theme_Color", "");
                SkinManager.getInstance().changeSkin(theme_color);
                dismiss();
                break;
            case R.id.tv_confirm:
                if (Theme_Color != null && (!Theme_Color.equals(""))){
                    SharedPreferenceUtil.saveData(getActivity(), "Theme_Color", Theme_Color);
                }
                dismiss();
                break;
            case R.id.tv_restore:
                SkinManager.getInstance().removeAnySkin();
                break;
            default:
                break;
        }
    }
}
