package com.akmnj.maalgaadiapp;






import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashActivity extends Activity {
boolean b;
Context con;
//Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		con=this;
		h.sendEmptyMessageDelayed(0, SPLASH_TIME_OUT);
       
	
	}
	
	Handler h=new Handler(){
		public void handleMessage(android.os.Message msg) {
			b=true;
			//Intent i = new Intent(con, MenuActivity.class);
			Intent i1=new Intent("abc");
			startActivity(i1);
		};
	};
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	b=false;
finish();	
}		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
