package menu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import com.aylong.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JayZhao
 * @date 2020/5/25
 */
public class StreamDeskMenuView extends LinearLayout {
    private Context mContext;
    private OnSettingMenuListener mMenuListener;
    private List<SecondLevelMenu> mSecondLevelMenuList;

    private final int[] ids = new int[]{R.mipmap.play, R.mipmap.calculator, R.mipmap.calendar};
    private ScrollView mScrollView;


    public StreamDeskMenuView(Context context) {
        super(context);
        init(context);
    }

    public StreamDeskMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StreamDeskMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        FirstLevelMenu firstLevelMenu = new FirstLevelMenu(context);
        firstLevelMenu.setOnFirstMenuCloseListener(menuCloseListener);
        firstLevelMenu.setImageList(ids);
        firstLevelMenu.setOnFirstMenuClickListener(menuClickListener);
        addView(firstLevelMenu);

        mScrollView = new ScrollView(context);
        mScrollView.setVisibility(GONE);
        addView(mScrollView);

        FrameLayout.LayoutParams params_s = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(VERTICAL);
        mScrollView.addView(layout, params_s);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        mSecondLevelMenuList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SecondLevelMenu secondLevelMenu = new SecondLevelMenu(context);
            secondLevelMenu.setMenuContent("quality level");
            secondLevelMenu.setChildList(list);
            mSecondLevelMenuList.add(secondLevelMenu);
            final int finalI = i;
            secondLevelMenu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeSecondMenuState(finalI);
                }
            });
            layout.addView(secondLevelMenu);
        }
    }

    private OnFirstMenuCloseListener menuCloseListener = new OnFirstMenuCloseListener() {
        @Override
        public void onFirstMenuClosed() {
            if (mScrollView != null && mScrollView.getVisibility() == VISIBLE) {
                mScrollView.setVisibility(GONE);
                if (mSecondLevelMenuList == null){return;}
                for (int i = 0; i < mSecondLevelMenuList.size(); i++) {
                    mSecondLevelMenuList.get(i).resetState();
                }
            }
        }
    };

    /**
     * 二级菜单状态变化
     *
     * @param position
     */
    private void changeSecondMenuState(int position) {
        if (mSecondLevelMenuList == null){return;}
        for (int i = 0; i < mSecondLevelMenuList.size(); i++) {
            mSecondLevelMenuList.get(i).changeMenuState(position == i);
        }
    }

    /**
     * 一级菜单点击监听
     */
    private FirstLevelMenu.OnFirstMenuClickListener menuClickListener = new FirstLevelMenu.OnFirstMenuClickListener() {
        @Override
        public void onMenuClick(int position) {
            switch (position) {
                case 0:
                    if (mMenuListener != null) {
                        // exit
                    }
                    break;
                case 1:
                    mScrollView.setVisibility(mScrollView.getVisibility() == VISIBLE ? GONE : VISIBLE);
                    break;
                case 2:
                    // 虚拟键盘
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置菜单点击监听
     *
     * @param listener
     */
    public void setOnSettingMenuListener(OnSettingMenuListener listener) {
        mMenuListener = listener;
    }

    public interface OnFirstMenuCloseListener {
        /**
         * 监听一级菜单关闭，同时关闭二级菜单
         */
        void onFirstMenuClosed();
    }
}
