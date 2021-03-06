package menu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.aylong.myapplication.R;

/**
 * @author JayZhao
 * @date 2020/5/25
 **/
public class FirstLevelMenu extends LinearLayout {
    private Context mContext;
    private ImageView settingImage;
    private StreamDeskMenuView.OnFirstMenuCloseListener mMenuCloseListener;
    private final int[] ids = new int[]{R.mipmap.netboom_menu_turn_off,
            R.mipmap.netboom_menu_settings_unsel,
            R.mipmap.netboom_menu_keybords};
    /**
     * 业务操作监听
     */
    private OnFirstMenuClickListener mMenuListener;
    /**
     * 右侧伸缩按钮
     */
    private ImageView mImg_change;
    /**
     * 内部伸缩linearLayout
     */
    private LinearLayout mInnerLl;
    /**
     * 内部伸缩linearLayout最大宽度
     */
    private int mMaxWidth;
    /**
     * 是否正在进行伸缩动画
     */
    private boolean isAnimating = false;
    /**
     * 当前menu的状态 0：展开  1：关闭
     */
    private int mMenuState = 0;
    /**
     * menu展开状态
     */
    private final int MENU_SHAPE_EXPAND = 0;
    /**
     * menu 关闭状态
     */
    private final int MENU_SHAPE_CLOSE = 1;

    public FirstLevelMenu(Context context) {
        super(context);
        init(context);

    }

    public FirstLevelMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public FirstLevelMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setImageList();
    }

    /**
     * 设置菜单icon
     */
    private void setImageList() {
        int side = dip2px(mContext, 28);
        LayoutParams params = new LayoutParams(side, side);
        params.leftMargin = dip2px(mContext, 16);
        params.topMargin = dip2px(mContext, 6);
        params.bottomMargin = dip2px(mContext, 6);

        if (ids == null || ids.length == 0) {
            return;
        }
        removeAllViews();
        mInnerLl = new LinearLayout(mContext);
        mInnerLl.setOrientation(HORIZONTAL);
        for (int i = 0; i < ids.length; i++) {
            ImageView img = new ImageView(mContext);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i == 1){
                settingImage = img;
                img.setImageResource(R.drawable.netboom_setting_bg_selector);
            }else {
                img.setImageResource(ids[i]);
            }
            final int finalI = i;
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuListener != null) {
                        mMenuListener.onMenuClick(finalI);
                    }
                }
            });
            mInnerLl.addView(img, params);
        }
        mInnerLl.post(new Runnable() {
            @Override
            public void run() {
                mMaxWidth = mInnerLl.getWidth();
            }
        });

        mImg_change = new ImageView(mContext);
        mImg_change.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImg_change.setImageResource(R.mipmap.netboom_menu_arrow_right);
        mImg_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMenuState();
            }
        });
        addView(mInnerLl);
        addView(mImg_change, params);
    }

    public void setSettingState(boolean sel){
        if (settingImage != null){
            settingImage.setSelected(sel);
        }
    }

    /**
     * 伸缩菜单
     */
    private void changeMenuState() {
        int x_begin, x_end;
        int r_begin, r_end;
        if (isAnimating) {
            return;
        }
        if (mMenuState == MENU_SHAPE_CLOSE) {
            x_begin = -mMaxWidth;
            x_end = 0;
            r_begin = 180;
            r_end = 360;
            mMenuState = MENU_SHAPE_EXPAND;
        } else {
            x_begin = 0;
            x_end = -mMaxWidth;
            r_begin = 0;
            r_end = 180;
            mMenuState = MENU_SHAPE_CLOSE;
            if (mMenuCloseListener != null) {
                mMenuCloseListener.onFirstMenuClosed();
            }
        }
        ObjectAnimator animator_trans = ObjectAnimator.ofFloat(this, TRANSLATION_X, x_begin, x_end);
        animator_trans.removeAllListeners();
        animator_trans.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator animator_rotate = ObjectAnimator.ofFloat(mImg_change, ROTATION, r_begin, r_end);
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator_trans).with(animator_rotate);
        animatorSet.setDuration(300);
        animatorSet.start();
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setOnFirstMenuClickListener(OnFirstMenuClickListener listener) {
        mMenuListener = listener;
    }

    /**
     * menu点击回调接口
     */
    public interface OnFirstMenuClickListener {
        /**
         * 记录一级菜单的点击位置，并回调给上一级别去动态显示二级菜单
         *
         * @param position 点击下标
         */
        void onMenuClick(int position);
    }

    /**
     * 设置一级菜单关闭监听
     *
     * @param listener
     */
    public void setOnFirstMenuCloseListener(StreamDeskMenuView.OnFirstMenuCloseListener listener) {
        mMenuCloseListener = listener;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
