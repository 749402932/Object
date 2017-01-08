package com.keren.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.Toast;


import com.keren.MyApplication;
import com.keren.view.CircleMenuLayout;
import com.keren.view.CircleMenuLayout.OnMenuItemClickListener;
import com.keren.view.FloatView;
import com.keren.view.Rotate3dAnimation;

import com.nineoldandroids.view.ViewHelper;


public class MainActivity extends FragmentActivity implements OnClickListener {

    private CircleMenuLayout mCircleMenuLayout;
    private DrawerLayout kemuDrawerLayout;
    public String[] itemStr = new String[]{"火车票查询", "天气查询", "快递查询", "笔记", "手机号查询", "身份证查询",};
    private int[] icons = new int[]{R.drawable.sup1, R.drawable.sup2, R.drawable.sup6,
            R.drawable.sup7, R.drawable.sup8, R.drawable.sup9};
    private Boolean needKey=true;		//是否需要密码
    private SharedPreferences sp;
    private Dialog keyDialog;
    private boolean isFirst = true;
    private EditText keyTxt;
    private int flags = 1;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams windowManagerParams = null;
    private FloatView floatView = null;
    private FloatView newsBlogView = null;
    private boolean isFisrtView = true;
    private int viewCount = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.set_activity, R.anim.hold);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mains);
        sp = getSharedPreferences("setting", 0);
        SysApplication.getInstance().addActivity(this);
        initView();
        initEvent();
        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(icons, itemStr);


        mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public void itemClick(View view, int pos) {
                toOtherActivity(pos);

            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(MainActivity.this,
                        " 加油",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void initView() {
        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        kemuDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        kemuDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
    }

    public void initEvent() {
        kemuDrawerLayout.setDrawerListener(new DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // TODO Auto-generated method stub
                View mContent = kemuDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.4f * scale;
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.5f + 0.6f * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));//设置偏移量
                    mContent.invalidate();
                    ViewHelper.setScaleY(mContent, rightScale);
                    ViewHelper.setScaleX(mContent, rightScale);
                } else {
                    ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    mContent.invalidate();
                    ViewHelper.setScaleY(mContent, rightScale);
                    ViewHelper.setScaleX(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerClosed(View arg0) {
                // TODO Auto-generated method stub
                kemuDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);


            }
        });
    }

    public void toOtherActivity(int position) {
        Log.v("dsfgergdf", position + "");

        switch (position) {
            case 0:
                FloatView.setClick();
                if (isFisrtView && viewCount == 0) {
                    createView(0);
                    isFisrtView = false;
                    viewCount += 1;
                }

                MyAnimation(0);


                break;
            case 1:
                FloatView.setClick();
                if (isFisrtView && viewCount == 0) {
                    createView(1);
                    isFisrtView = false;
                    viewCount++;
                }
                MyAnimation(1);


                break;


            case 2:
                FloatView.setClick();
                if (isFisrtView && viewCount == 0) {
                    createView(2);
                    isFisrtView = false;
                    viewCount++;
                }
                MyAnimation(2);


                break;
            case 3:
                FloatView.setClick();
                if (isFisrtView && viewCount == 0) {
                    createView(3);
                    isFisrtView = false;
                    viewCount++;
                }
                MyAnimation(3);

                break;
            case 4:
                FloatView.setClick();
                if (isFisrtView && viewCount == 0) {
                    createView(4);
                    isFisrtView = false;
                    viewCount++;
                }
                MyAnimation(4);

                break;
            case 5:

                FloatView.setClick();
                if (isFisrtView && viewCount == 0) {
                    createView(5);
                    isFisrtView = false;
                    viewCount++;
                }
                MyAnimation(5);

                break;


        }
    }


    private void createView(int i) {


        // 获取WindowManager
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        // 设置LayoutParams(全局变量）相关参数
        windowManagerParams = ((MyApplication) getApplication()).getWindowParams();

        windowManagerParams.type = LayoutParams.TYPE_PHONE; // 设置window type
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // 设置Window flag
        windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE;
              /*
	  		 * 注意，flag的值可以为：
	  		 * 下面的flags属性的效果形同“锁定”。
	  		 * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
	  		 * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
	  		 * LayoutParams.FLAG_NOT_FOCUSABLE  不可聚焦
	  		 * LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
	  		 */
        // 调整悬浮窗口至左上角，便于调整坐标

        // 设置悬浮窗口长宽数据
        windowManagerParams.width = LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = LayoutParams.WRAP_CONTENT;
        if (i == 5) {

            floatView = new FloatView(getApplicationContext());
            floatView.setOnClickListener(this);

            floatView.setImageResource(R.drawable.kemubu); // 这里简单的用自带的icon来做演示
            windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
            // 以屏幕左上角为原点，设置x、y初始值
            windowManagerParams.x = 0;
            windowManagerParams.y = 0;
            windowManager.addView(floatView, windowManagerParams);

        } else {
            if (FloatView.getCount() == 0) {
                newsBlogView = new FloatView(getApplicationContext());


                newsBlogView.setOnClickListener(new NBClickListener(i));

                newsBlogView.setImageResource(R.drawable.kerenke22); // 这里简单的用自带的icon来做演示
                windowManagerParams.x = -mCircleMenuLayout.getWidth();
                windowManagerParams.y = mCircleMenuLayout.getHeight();
                windowManagerParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                windowManager.addView(newsBlogView, windowManagerParams);
            }


        }


    }

    public void onClick(View v) {

        Intent to6 = new Intent();
        if (flags == 1) {
            flags = 0;
        } else {
            flags = 1;
        }
        to6.putExtra("flags", flags);
        to6.setAction("com.keren.media.MUSIC_SERVICE");

        startService(to6);
    }

    public final class NBClickListener implements OnClickListener {
        int i;

        public NBClickListener(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            i++;
            i = i % 5;


        }

    }

    public void MyAnimation(int i) {

        float j = (i + 1) % 3;


        float centerX = mCircleMenuLayout.getWidth() / 2f;
        float centerY = mCircleMenuLayout.getHeight() / 2f;
        // 构建3D旋转动画对象，旋转角度为0到90度，这使得ListView将会从可见变为不可见
        final Rotate3dAnimation rotation = new Rotate3dAnimation(0, 90 * j, centerX, centerY,
                310.0f, true);
        // 动画持续时间500毫秒
        rotation.setDuration(500);
        // 动画完成后保持完成的状态

        rotation.setInterpolator(new AccelerateInterpolator());
        mCircleMenuLayout.startAnimation(rotation);
        rotation.setFillAfter(true);
        // 设置动画的监听器
        rotation.setAnimationListener(new TurnToImageView(i));


    }

    class TurnToImageView implements AnimationListener {
        int i;

        public TurnToImageView(int i) {
            this.i = i;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        /**
         * 当ListView的动画完成后，还需要再启动ImageView的动画，让ImageView从不可见变为可见
         */
        @Override
        public void onAnimationEnd(Animation animation) {

            switch (i) {
                case 0:
                    Intent to0 = new Intent();
                    to0.setClass(MainActivity.this, HuoChe.class);
                    startActivity(to0);
                    MainActivity.this.finish();


                    break;
                case 1:
                    Intent to1 = new Intent();
                    to1.setClass(MainActivity.this, Weather.class);
                    startActivity(to1);
                    MainActivity.this.finish();
                    break;
                case 2:
                    Intent to3 = new Intent();
                    to3.setClass(MainActivity.this, KuaiDiMainActivity.class);
                    startActivity(to3);
                    MainActivity.this.finish();

                    break;
                case 3:
                    Intent data=getIntent();

                    if(sp.contains("key")){
                        if(sp.getString("key","")!="")
                        enterKey();
                        else
                        {
                            Intent intent=new Intent(MainActivity.this,BiJiMainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                    else
                    {
                        Intent intent=new Intent(MainActivity.this,BiJiMainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    break;
                case 4:
                    Intent to6 = new Intent();
                    to6.setClass(MainActivity.this, PhoneFindActivity.class);
                    startActivity(to6);
                    MainActivity.this.finish();

                    break;

                case 5:
                    Intent to7 = new Intent();
                    to7.setClass(MainActivity.this, IDCardActivity.class);
                    startActivity(to7);
                    MainActivity.this.finish();

                    break;
                default:
                    break;
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {//退出键

            SysApplication.getInstance().exit();
        }

        return super.onKeyDown(keyCode, event);
    }

    private void enterKey()			//输入密码
    {
        View keyView = View.inflate(this, R.layout.cancelkey, null);
        keyDialog=new Dialog(this,R.style.dialog);
        keyDialog.setContentView(keyView);
        keyTxt=(EditText)keyView.findViewById(R.id.key_old);
        keyTxt.addTextChangedListener(change);
        keyDialog.show();
    }
    public TextWatcher change = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String okey=keyTxt.getText().toString();
            String rkey=sp.getString("key", "");
            if(okey.equals(rkey))
            {
                needKey=false;
                keyDialog.dismiss();
                Intent intent=new Intent(MainActivity.this,BiJiMainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

}
