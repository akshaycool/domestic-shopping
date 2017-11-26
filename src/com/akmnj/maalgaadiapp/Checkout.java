package com.akmnj.maalgaadiapp;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Checkout extends Activity{
	float total;static String temp_pro_name;static Button chk_out;static String item_list;
	static int product_no;
	TextView tottv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);
		item_list=new String();
		showDB();
	}
	

	public void showDB(){
		total=0;
		Dbhelp dbh=new Dbhelp(this, "maalgaadi1.db");
		SQLiteDatabase dbr=dbh.getReadableDatabase();
		TableLayout tl=(TableLayout)findViewById(R.id.chk_tl);
		TableRow td= new TableRow(getApplicationContext());
		td.setGravity(Gravity.RIGHT);
		/*ImageView imv= new ImageView(getApplicationContext());
		imv.setClickable(true);
		imv.setImageResource(R.drawable.call);
		timv.addView(imv);*/
		Button order_now=new Button(getApplicationContext());
		MarginLayoutParams mlp= new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//mlp.leftMargin=200;
		mlp.topMargin=20;
		/*order_now.setLayoutParams(mlp);
		order_now.setText("Order now");
		order_now.setBackgroundResource(R.drawable.call);
		td.addView(order_now);
		order_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Intent.ACTION_CALL,Uri.parse("tel:8879018120"));
				startActivity(i);
			}
		});
		*/
		tl.removeAllViews();
		tl.addView(td);
		tl.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
		item_list="";
		
		String all[]={"_id","name","mrp"};
		final Cursor cr=dbr.query("cart",all , null,null,null,null,null);
		cr.moveToFirst();
		product_no=0;
		while(!cr.isAfterLast())
		{
			TableRow tr=new TableRow(this);
			tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
			TextView tv[]=new TextView[3];
			tv[0]=new TextView(this); tv[0].setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));
			tv[1]=new TextView(this);tv[1].setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 8));
			tv[2]=new TextView(this);tv[2].setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 2));
			tv[1].setGravity(Gravity.CENTER_VERTICAL);
			tr.setGravity(Gravity.CENTER);
			tv[0].setTextSize(20);
			tv[1].setTextSize(20);
			tv[2].setTextSize(20);
			tr.setBackgroundColor(getResources().getColor(R.color.White));
			tv[2].setBackgroundColor(getResources().getColor(R.color.White));
			tv[2].setGravity(Gravity.CENTER);
			Button cancel=new Button(this);
			cancel.setText("X");
			final String pro_nm=cr.getString(1);

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Dbhelp dbh=new Dbhelp(MenuActivity.con, "maalgaadi1.db");
					SQLiteDatabase dbr=dbh.getReadableDatabase();
					String arr[]={pro_nm};
					dbr.delete("cart","name=?" , arr);
					dbr.close();
					dbh.close();
					showDB();
				}
			});
			product_no++;
			tv[0].setText(product_no+". ");
			tv[1].setText(cr.getString(1));
			tv[1].setSingleLine(false);
			item_list=item_list+product_no+"."+cr.getString(1)+"\n";
			tv[2].setText(cr.getString(2));
			total+=Float.parseFloat(cr.getString(2));
			cancel.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
			tr.addView(tv[0]);
			tr.addView(tv[1]); 
			tr.addView(tv[2]);
			tr.addView(cancel);
			
			tl.addView(tr);
			View v=new View(this);
			v.setBackgroundColor(getResources().getColor(R.color.Black));
			v.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2));
			tl.addView(v);
			cr.moveToNext();
		}
		View v=new View(this);
		CheckBox cb=new CheckBox(getApplicationContext());
		cb.setText("Plastic Bags required?");
		cb.setChecked(true);
		cb.setTextColor(Color.BLUE);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			if(isChecked){
				total+=product_no;
				tottv.setText(new Float(total+0.5).intValue()+"");
				
				Toast.makeText(getApplicationContext(), "The bag will cost u extra one rupee per product",Toast.LENGTH_LONG).show();					
			}
			else{
				Toast.makeText(getApplicationContext(), "You won't incurr extra charges",Toast.LENGTH_LONG).show();
				total-=product_no;
				tottv.setText(new Float(total+0.5).intValue()+"");
			}
			
			
			}});
		
		TextView tv123=new TextView(this);
		tv123.setText("Total cost of items:");
	    tottv=new TextView(this);
		tottv.setText(new Float(total+0.5).intValue()+"");
		TableRow trp=new TableRow(this);
		TableRow trf=new TableRow(this);
		trp.addView(cb);
		trf.addView(v);
		trf.addView(tv123);
		trf.addView(tottv);
		tl.addView(trp);
		tl.addView(trf);
		chk_out=new Button(this);
		chk_out.setText("Checkout!");
		Button order=new Button(this);
		order.setText("Call us");
		order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Intent.ACTION_CALL,Uri.parse("tel:8879018120"));
				startActivity(i);
			}
		});
		
		
		TableRow trf1=new TableRow(this);
		trf1.addView(chk_out);
		trf1.addView(order);
		tl.addView(trf1);
		tl.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.WRAP_CONTENT));
	}
public void orderMy(View v){
			
}	 
	
	public static Dialog getReg(Context c){
		final Dialog d=new Dialog(c);
		d.setContentView(R.layout.register);
		Button reg= (Button)d.findViewById(R.id.register_but);
		reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name=((EditText)d.findViewById(R.id.rname)).getText().toString();
				String no=((EditText)d.findViewById(R.id.rphone)).getText().toString();
				String add=((EditText)d.findViewById(R.id.raddress)).getText().toString();
				if(name.length()<5)
				{Toast.makeText(arg0.getContext(), "Please enter proper full name", Toast.LENGTH_LONG).show();return;}
				if(no.length()!=10)
				{Toast.makeText(arg0.getContext(), "Please enter valid indian 10 digit no.", Toast.LENGTH_LONG).show();return;}
				if(add.length()<5)
				{Toast.makeText(arg0.getContext(), "Please enter proper address", Toast.LENGTH_LONG).show();return;}
				SharedPreferences prefs=arg0.getContext().getSharedPreferences("com.akmnj.maalgaadiapp", Context.MODE_PRIVATE);
				Editor ed=prefs.edit();
				ed.putString("name", name);
				ed.putString("no", no);
				ed.putString("address", add);
				ed.commit();
				d.dismiss();
			}
		});
		return d;
	}

	
	class Chk_listener implements OnClickListener{
		String itm_list; float prc;Context c;
		boolean success;
		Chk_listener(Context cnt,String item_list, float price){
			itm_list=item_list.toLowerCase();
			itm_list=itm_list.replace("\n", "   ");
			itm_list=itm_list.replace(" ", "%20");
			
			itm_list=itm_list.replace("'s", "");
			itm_list=itm_list.replace("62", "");
			prc=price;
			c=cnt;
			success=false;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final	SharedPreferences prefs=c.getSharedPreferences("com.akmnj.maalgaadiapp", Context.MODE_PRIVATE);
			if(!prefs.contains("name")&&!prefs.contains("no")&&!prefs.contains("address"))
			{
				Toast.makeText(c,"There seems to be a problem with your registration. Please register yourself to use the app", Toast.LENGTH_LONG).show();
				Checkout.getReg(c).show();
				return;
			}
			if(!isConnectedToInternet(c))
			{
				Dialog d=new Dialog(v.getContext());
				d.setTitle("No Internet Connection detected! Please ensure a working net connection for a succesful checkout");
				d.show();
				return;
			}
			else
			{
				class Send extends AsyncTask<Void,String,String>{
					ProgressDialog progress;
					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						progress = new ProgressDialog(c); 
						progress.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
						progress.setMessage("Please wait... "); 
						progress.show(); 		
					}
					@Override
					protected String doInBackground(Void... S) {
						// TODO Auto-generated method stub
						ArrayList<? extends NameValuePair> nameValuePair=new ArrayList<NameValuePair>();
						try{
							HttpClient httpclient = new DefaultHttpClient();
							Log.e("MAAL URL","http://manojm20.link4.in/Maalgaadi/chk_out.php?name="+prefs.getString("name", "dummy").replace(" ", "%20")+"&no="+prefs.getString("no", "dummy")+"&items="+itm_list+"&price="+prc);
							HttpPost httppost = new HttpPost("http://manojm20.link4.in/Maalgaadi/chk_out.php?name="+prefs.getString("name", "dummy").replace(" ", "%20")+"&no="+prefs.getString("no", "dummy")+"&items="+itm_list+"&price="+prc);

							httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));

							HttpResponse response = httpclient.execute(httppost);

							Log.e("MAAL","succesful checkout");
							HttpEntity entity = response.getEntity();
						InputStream	is = entity.getContent();
						success=true;

						}catch(Exception e){
							Log.e("log_tag", "Error in http connection "+e.toString());
						    success=false;
						}
						return null;
					}
					@Override
					protected void onPostExecute(String result) {
						// TODO Auto-generated method stub
						progress.dismiss();
						
						if(success)
						{
							
							
							Intent i=new Intent(Intent.ACTION_CALL,Uri.parse("tel:8879018120"));
							startActivity(i);			
					/*	Dialog d=new Dialog(c);
						d.setTitle("Checkout Succesful! Our representative will contact you shortly");
						d.show();
					*/	
						Dbhelp dbh=new Dbhelp(c, "maalgaadi1.db");
						SQLiteDatabase dbr=dbh.getReadableDatabase();
						dbr.delete("cart", null, null);
						dbr.close();
						dbh.close();
						showDB();
						}
						else
						{
							Dialog d=new Dialog(c);
							d.setTitle("Checkout UnSuccesful! Please check your internet connection and try again later");
							d.show();
							
						}
						super.onPostExecute(result);
					}

				};
				(new Send()).execute();
				
			}
		}


	}

	public boolean isConnectedToInternet(Context con){
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                  {
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
                  }
          }
          return false;
    }

	
	
	
}

