package com.keren.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

public class WelcomeActivity extends Activity {
	MarqueTextView tv_version;
	RelativeLayout container;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//final Window window = getWindow();// 获取当前的窗体对象
	//	window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏了状态栏
		//requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏了标题栏
		setContentView(R.layout.welcome_view);
		container=(RelativeLayout)findViewById(R.id.container);
		tv_version=(MarqueTextView) findViewById(R.id.tv_version);
		
		ImageView imageView = (ImageView) findViewById(R.id.iv_toMain);// 查找到对应的ImageView
		imageView.setImageResource(R.drawable.yoo_head);// 注意drawable中的图片的name必须是小写字母
		//ha是动画集合
		Animation a=AnimationUtils.loadAnimation(this,R.anim.ha);
		imageView.startAnimation(a);
		a.setFillAfter(true);
		welcomeUI();
	}

	private void welcomeUI()
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					Thread.sleep(2000);
					Message message = new Message();
					welHandler.sendMessage(message);// 具体消息中包含什么东西并不重要，因为接收的函数不需要该参数
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	Handler welHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			welcomeFunction();
		}

	};

	public void welcomeFunction()
	{
		initadv();
	}

  void initadv(){

	  //创建开屏广告，广告拉取成功后会自动展示在container中。Container会首先被清空
	  new SplashAD(this, container, "1105250689", "9060019039011993",
			  new SplashADListener() {
				  @Override
				  public void onADDismissed() {
					  Intent intent = new Intent();
					  intent.setClass(WelcomeActivity.this, MainActivity.class);
					  startActivity(intent);
					  WelcomeActivity.this.finish();
				  }

				  @Override
				  public void onNoAD(int i) {
					  Intent intent = new Intent();
					  intent.setClass(WelcomeActivity.this, MainActivity.class);
					  startActivity(intent);
					  WelcomeActivity.this.finish();
				  }

				  @Override
				  public void onADPresent() {

				  }

				  @Override
				  public void onADClicked() {

				  }

			  });
  }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//阻止用户在展示过程中点击手机返回键，推荐开发者使用
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
}
