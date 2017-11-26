package com.akmnj.maalgaadiapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.akmnj.maalgaadiapp.R.color;

@SuppressLint("NewApi")
public class SubCategorylist extends SherlockActivity {
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.sub_categorylist, menu);
		SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		com.actionbarsherlock.widget.SearchView searchView =(com.actionbarsherlock.widget.SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return super.onCreateOptionsMenu(menu);
	}
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,MenuActivity.class));
		super.onBackPressed();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "MENU!!!", Toast.LENGTH_LONG).show();
	if(item.getItemId()==android.R.id.home){
       // sideNavigationView.toggleMenu();
	     startActivity(new Intent(this,MenuActivity.class));
     //   Toast.makeText(this, "NAvigate selected",Toast.LENGTH_SHORT).show();
	}
//This section comment kar//
	/*if(item.getItemId()==R.id.search){
	       // sideNavigationView.toggleMenu();
		Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this,MenuActivity.class));
	     //   Toast.makeText(this, "NAvigate selected",Toast.LENGTH_SHORT).show();
		}	
	*/
	return super.onOptionsItemSelected(item);
	}


	private ListView sublist;
	private TextView categorytv;
	//private String[]babycare={}; 
	Dbhelp dbh;SQLiteDatabase dbr;
	Drawable d;
	LinearLayout l3;
	Context con;
	LinearLayout ll_Mparent;
	String selargs[];
	ProgressDialog pd;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_categorylist);
		con=this;
		//categorytv=(TextView)findViewById(R.id.category);
         selargs=new String[1];
         selargs[0]=new String();
        final String category=getIntent().getStringExtra(MenuActivity.EXTRA_TITLE);
      
         //categorytv.setTypeface(Typeface.SERIF,Typeface.BOLD_ITALIC);         
         selargs[0]=getIntent().getStringExtra(MenuActivity.EXTRA_TITLE);
         selargs[0]=selargs[0].toLowerCase();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#58ee0f")));
		getSupportActionBar().setTitle(category);
		sublist=(ListView)findViewById(android.R.id.list);
		pd=new ProgressDialog(this);
		pd.setMessage("Please wait... Contents Loading");
		pd.show();
		class Load_cat extends AsyncTask<Void, Void, Void>
		{

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				l3=new LinearLayout(con);
				//
				dbh=new Dbhelp(con, "maalgaadi1.db");
				dbr=dbh.getWritableDatabase();	
				LinearLayout ll=new LinearLayout(con);
				
				ll.setBackgroundColor(color.white);
				MarginLayoutParams mlp=new MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,ViewGroup.MarginLayoutParams.MATCH_PARENT);
				mlp.setMargins(250,0,0,0);
				//categorytv.setText(category);
				//mlp.topMargin=60;		
				TextView tv1=new TextView(con);
				tv1.setTypeface(Typeface.SERIF);
				tv1.setText(category);
				tv1.setGravity(Gravity.CENTER);
				tv1.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#58ee0f")));
				tv1.setTextSize(20);
				tv1.setLayoutParams(mlp);
				ll.addView(tv1);
				ll.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
				ll.setOrientation(LinearLayout.VERTICAL);
				String all[]={"name","mrp","description"};
				Cursor cr=dbr.query("maalgaadim", all,"cat=?",selargs,null,null,null);
				Log.e("MAAL", "Cursor count: "+cr.getCount());
				cr.moveToFirst();
				TextView tv[]=new TextView[2];
				int ik=0;
				while(!cr.isAfterLast())
				{   ik++;
					LinearLayout llc=new LinearLayout(con);
					llc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
					tv[0]=new TextView(con);tv[0].setBackgroundColor(getResources().getColor(R.color.White));
					tv[1]=new TextView(con);
					tv[1].setBackgroundColor(getResources().getColor(R.color.White));
					tv[0].setClickable(true);
					tv[0].setText(cr.getString(0)+"   ");
					tv[1].setText(cr.getString(1));
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
					tv[0].setOnClickListener(new Add_to_cart(cr.getString(0),cr.getString(1),cr.getString(2),con));
					cr.moveToNext();
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
				ImageView iv2=new ImageView(con);
				iv2.setClickable(true);
				ImageView iv3=new ImageView(con);
				iv3.setClickable(true);
				iv2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					 Intent i=new Intent(getApplicationContext(),MenuActivity.class);
					 i.putExtra("frm_img",true);
					startActivity(i);	
					}
				});
				iv3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(getApplicationContext(),Checkout.class);
						startActivity(i);
					}
				});
				iv2.setBackgroundResource(R.drawable.shop);
				iv3.setBackgroundResource(R.drawable.checkout);
				iv2.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
				iv3.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
				l3.setBackgroundColor(getResources().getColor(R.color.transparent));
				ll_Mparent=new LinearLayout(con);
				ll_Mparent.setOrientation(LinearLayout.VERTICAL);		
				l3.addView(iv2);
				if(Build.VERSION.SDK_INT<11)
				{
					View v=new View(con);
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
				
				dbr.close();
				//dbw.close();
				dbh.close();
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				pd.dismiss();
				setContentView(ll_Mparent);
				super.onPostExecute(result);
			}
			
		}
		new Load_cat().execute();
			
	}

		
	static class Add_to_cart implements View.OnClickListener{
		String nm,price,desc;Context cont;
		public Add_to_cart(String name,String price, Context con){
			nm=name; this.price=price;cont=con;desc="description not available";
		}
		public Add_to_cart(String name,String price, String desc,Context con){
			nm=name; this.price=price;cont=con;this.desc=desc;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final Dialog d=new Dialog(cont);
			LinearLayout ll=new LinearLayout(cont);
			ll.setOrientation(LinearLayout.VERTICAL);
			LinearLayout ll1=new LinearLayout(cont);
			ll1.setOrientation(LinearLayout.HORIZONTAL);
			TextView desctv=new TextView(cont);
			desctv.setText(desc);
			desctv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			desctv.setBackgroundColor(cont.getResources().getColor(R.color.black));
			desctv.setTextColor(cont.getResources().getColor(R.color.white));
			
			ll1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			TextView tv=new TextView(cont);
			tv.setText("Do you want to add "+nm+" to the cart? This Item will cost "+price+"\n");
			tv.setTextSize(25);
			tv.setTextColor(cont.getResources().getColor(R.color.white));
			Button yes=new Button(cont);
			yes.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
			tv.setBackgroundColor(cont.getResources().getColor(R.color.black));
			tv.setTextColor(cont.getResources().getColor(R.color.white));
			Button no=new Button(cont);
			no.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
			yes.setText("YES");
			yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Dbhelp dbhoc=new Dbhelp(cont, "maalgaadi1.db");
				SQLiteDatabase dbroc=dbhoc.getReadableDatabase();
				ContentValues cvs=new ContentValues();
				cvs.put("name", nm);
				cvs.put("mrp", price);
				long k=dbroc.insert("cart", null, cvs);
				
				Log.e("MAAL", k+"th product put into cart");
				if(k>0)
					Toast.makeText(cont, "Successfully added product into cart", Toast.LENGTH_SHORT).show();
				d.dismiss();
				dbroc.close();
				dbhoc.close();
			}
		});
			no.setText("NO");
			no.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					d.dismiss();
				}
			});
			ll1.addView(yes);
			ll1.addView(no);
			ll.addView(tv);
			ll.addView(desctv);
			ll.addView(ll1);
			d.setContentView(ll);
			d.show();
		}
	
	
	}

}