package com.akmnj.maalgaadiapp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akmnj.maalgaadiapp.SubCategorylist.Add_to_cart;


@SuppressLint("NewApi")
public class Search extends Activity{
	LinearLayout l3;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			// ........
			if(l3.getVisibility()==View.VISIBLE)
				l3.setVisibility(View.GONE);
			else
				l3.setVisibility(View.VISIBLE);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}


	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.i("intent","NEW");
		handleIntent(getIntent());
	}
	ProgressDialog pd;Context con;String to_srch;
	static float min,max;
	static String category;
	static int sort;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e("MAAL", "search.java reached");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_categorylist);
		con=this;
		pd=new ProgressDialog(con);
		pd.show();
		pd.setMessage("Searching.... please wait");
		//pd.setCancelable(false);
		min=getIntent().getFloatExtra("min_prc", 0);
		max=getIntent().getFloatExtra("max_prc", 99999);
		category=getIntent().getStringExtra("category");
		sort=getIntent().getIntExtra("sort", 1);
		/*
		 * SORT LEGEND: order by (no), where no: 
		 * 1. name ascending
		 * 2. name descending
		 * 3. price ascending
		 * 4. price descending
		 */
		handleIntent(getIntent());

		//hd.sendEmptyMessageDelayed(0, 1000);
	}
	Handler hd;
	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			to_srch = intent.getStringExtra(SearchManager.QUERY);
			new Load_src().execute();

		}
	}        


	class Load_src extends AsyncTask<Void,Void,Void>{

		boolean success;
		LinearLayout ll_Mparent;
		Dbhelp dbh;
		SQLiteDatabase dbr;
		int ik;
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			ll_Mparent=new LinearLayout(getApplicationContext());
			ll_Mparent.setOrientation(LinearLayout.VERTICAL);		
			success=false;
			TableLayout tl=new TableLayout(con);
			tl.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT));
			String all[]={"name","mrp","description","sell_price","cat"};
			dbh=new Dbhelp(con, "maalgaadi1.db");
			dbr=dbh.getWritableDatabase();
			String selargs[]=new String[4];
			selargs[0]="%"+to_srch+"%";
			if(category!=null)
				selargs[1]=category;
			else
				selargs[1]="%%";
			selargs[2]=min+"";
			selargs[3]=max+"";
			String order_by=new String();
			order_by="name ASC";
			switch(sort){
			case 1:{order_by="name ASC";break;}
			case 2:{order_by="name DESC";break;}
			case 3:{order_by="CAST(sell_price as real) ASC";break;}
			case 4:{order_by="CAST(sell_price as real) DESC";break;}
			}
			final Cursor cr=dbr.query("maalgaadim", all,"name like ? and cat like ? and CAST(sell_price as real) > ? and CAST(sell_price as real) < ?",selargs, null,null,order_by);
			Log.e("MAAL", "Cursor count: "+cr.getCount());
			cr.moveToFirst();
			TextView tv[]=new TextView[2];
			LinearLayout ll=new LinearLayout(con);
			ll.setOrientation(LinearLayout.VERTICAL);
			ik=0;
			while(!cr.isAfterLast())
			{
				ik++;
				LinearLayout llc=new LinearLayout(con);
				llc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				tv[0]=new TextView(con);tv[0].setBackgroundColor(getResources().getColor(R.color.BlanchedAlmond));
				tv[1]=new TextView(con);
				//tv[1].setBackgroundColor(getResources().getColor(R.color.Black));
				tv[0].setClickable(true);
				tv[0].setText(cr.getString(0)+"   ");
				tv[1].setText(Html.fromHtml("<s>"+cr.getString(1)+"</s>"+"  "+cr.getString(3)));
				llc.setOrientation(LinearLayout.HORIZONTAL);
				tv[0].setSingleLine(false);
				tv[0].setTextSize(20);
				tv[1].setTextSize(20);
				//tv[1].setTextColor(getResources().getColor(R.color.white));
				tv[0].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,8));
				tv[1].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,1));
				llc.addView(tv[0]);
				llc.addView(tv[1]);
				ll.addView(llc);
				View v=new View(con);
				v.setBackgroundColor(getResources().getColor(R.color.Black));
				v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2));
				ll.addView(v);
				tv[0].setOnClickListener(new Add_to_cart(cr.getString(0),cr.getString(3),cr.getString(2),con));
				cr.moveToNext();
				Log.e("MAAL", ik+"");

			}

			ScrollView sv=new ScrollView(con);
			l3=new LinearLayout(getApplicationContext());
			//l3.setClickable(true);
			if(Build.VERSION.SDK_INT>10)
			{
				l3.setDividerDrawable(new ColorDrawable(Color.WHITE));
				l3.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			}
			l3.setBackgroundColor(Color.BLACK);
			ImageView iv2=new ImageView(con.getApplicationContext());
			iv2.setClickable(true);
			ImageView iv3=new ImageView(con.getApplicationContext());
			iv3.setClickable(true);
			iv2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(con.getApplicationContext(),MenuActivity.class);
					i.putExtra("frm_img",true);
					startActivity(i);	
				}
			});
			iv3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(con.getApplicationContext(),Checkout.class);
					startActivity(i);
				}
			});
			iv2.setBackgroundResource(R.drawable.shop);
			iv3.setBackgroundResource(R.drawable.checkout);
			iv2.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
			iv3.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
			l3.setBackgroundColor(getResources().getColor(R.color.transparent));
			l3.addView(iv2);
			if(Build.VERSION.SDK_INT<11)
			{
				View v=new View(getApplicationContext());
				v.setLayoutParams(new LinearLayout.LayoutParams(2, LayoutParams.MATCH_PARENT));
				v.setBackgroundColor(getResources().getColor(R.color.Black));
				l3.addView(v);
			}
			l3.addView(iv3);
			//ll.addView(l3);
			sv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
			l3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			l3.setVisibility(View.GONE);
			ll_Mparent.addView(sv);
			sv.addView(ll);
			ll_Mparent.addView(l3);
			if(ik==0)
			{
				return null;
			}
			success=true;

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			setContentView(ll_Mparent);
			pd.dismiss();
			dbr.close();
			dbh.close();
			if(success)
				Toast.makeText(con, "Search completed: "+ik+" products found", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(con, "Sorry! Your search did not retrieve any results", Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

	}
}

