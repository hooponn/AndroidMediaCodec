package com.yocn.meida.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.yocn.libyuv.YUVTransUtil;
import com.yocn.media.R;
import com.yocn.meida.JumpBean;
import com.yocn.meida.camera.BaseCameraProvider;
import com.yocn.meida.util.DisplayUtil;
import com.yocn.meida.util.LogUtil;
import com.yocn.meida.view.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yocn
 */
public class MainActivity extends Activity {
    RecyclerView mRecyclerView;
    RelativeLayout mTopRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        View rootView = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(rootView);
        initView(rootView);
        initData();
    }

    private void initView(View root) {
        mRecyclerView = root.findViewById(R.id.rv_main);
        mTopRL = root.findViewById(R.id.rl_top);
        mTopRL.post(() -> {
            int height = getWindow().getDecorView().getMeasuredHeight();
            int width = getWindow().getDecorView().getMeasuredWidth();
            BaseCameraProvider.ScreenSize = new Size(width, height);
            BaseCameraProvider.TextureViewSize = DisplayUtil.getTextureViewSize(BaseCameraProvider.previewSize);
        });
    }

    private int currentY;

    private void initData() {
        String ss = new YUVTransUtil().stringFromJNI();
        LogUtil.d("ss->" + ss);
        List<JumpBean> list = new ArrayList<>();
//        list.add(new JumpBean("", PreviewPureActivity.class));
        list.add(new JumpBean("TextureView预览", PreviewPureActivity.class));
        list.add(new JumpBean("预览并获取数据", PreviewDataActivity.class));
        list.add(new JumpBean("Yuv数据获取", PreviewYUVDataActivity.class));
        list.add(new JumpBean("Yuv数据获取 方式2", PreviewYUVDataActivity2.class));
        list.add(new JumpBean("Native转换Yuv", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("libyuv做ARGB和I420转换", FormatTransportActivity.class));
        list.add(new JumpBean("GPUImage预览", PreviewGPUImageActivity.class));
        list.add(new JumpBean("3", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("4", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("5", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        list.add(new JumpBean("6", PreviewNativeYUVActivity.class));
        MainAdapter mMainAdapter = new MainAdapter(list);
        mMainAdapter.setmContext(this);
        int spanCount;
        if (list.size() < 6) {
            spanCount = 2;
        } else if (list.size() < 24) {
            spanCount = 3;
        } else {
            spanCount = 4;
        }
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                                @Override
                                                public int getSpanSize(int position) {
                                                    return position == 0 ? gridLayoutManager.getSpanCount() : 1;
                                                }
                                            }
        );

        final int min = DisplayUtil.dip2px(this, 100);
        final int max = DisplayUtil.dip2px(this, 140);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mMainAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentY += dy;
//                LogUtil.d("currentY->" + currentY + " dy->" + dy + " show->" + show);
                if (currentY < min) {
                    mTopRL.setVisibility(View.GONE);
                    DisplayUtil.setAndroidNativeLightStatusBar(MainActivity.this, false);
                } else {
                    mTopRL.setVisibility(View.VISIBLE);
                    DisplayUtil.setAndroidNativeLightStatusBar(MainActivity.this, true);
                    if (currentY < max) {
                        int percent = (currentY - min) * 100 / (max - min);
                        String color = DisplayUtil.getColor(percent);
                        mTopRL.setBackgroundColor(Color.parseColor(color));
//                        LogUtil.d("color->" + color + " percent->" + percent);
                    } else {
                        mTopRL.setBackgroundResource(R.color.gray);
                    }

                }
            }
        });
    }

}
