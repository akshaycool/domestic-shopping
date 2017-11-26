package com.akmnj.maalgaadiapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class Db{ 
	static int ex_code;
	static String final_str;


	//CHECK IF OUR DATABASE ALREADY EXISTS
	public static boolean db_exists(Context con){
		File dbFile=con.getDatabasePath("maalgaadi1.db");
		return dbFile.exists();
	}







	// dl DOWNLOADS THE ENTIRE PRODUCT LIST AND STORES IT AS A SINGLE STRING IN final_str


	//CREATES A NEW DATABASE USING CONTENTS STORED IN final_str
	public static void new_db(Context cont){
		{
			final Context con=cont;
			class DB_create extends AsyncTask<Void,Void,String>{
				ProgressDialog p;
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					p=new ProgressDialog(con);
					p.setTitle("Database loading... please wait");
					p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					p.show();
				}
				@Override
				protected String doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					Dbhelp dbh=new Dbhelp(con, "maalgaadi1.db");
					SQLiteDatabase dbr=dbh.getReadableDatabase();
					//if(Db.db_exists())
					//dbr.delete("maalgaadi", null, null)
					//;
					ContentValues cvs=new ContentValues();
					try{
						InputStream is=con.getAssets().open("Maalgaadi.csv");
						BufferedReader reader=new BufferedReader(new InputStreamReader(is));
						reader.readLine();

						String line=new String();
						int i=0;long k=0;

						while((line=reader.readLine())!=null)
						{
							String entries[]=line.split(",",10);
							cvs.clear();
							cvs.put("name",entries[0]);
							cvs.put("cat",entries[1].toLowerCase());
							cvs.put("sub_cat",entries[2]);
							cvs.put("mrp",entries[3]);
							cvs.put("vat",entries[4]);
							cvs.put("cost_price",entries[5]);
							cvs.put("sell_price",entries[6]);
							cvs.put("discount",entries[7]);
							cvs.put("discount_percent",entries[8]);
							cvs.put("description",entries[9]);
							k=dbr.insert("maalgaadim",null,cvs);
							Log.e("MAAL",k+" Entered into database:"+entries[0]+entries[1]+entries[2]+entries[3]+entries[4]+entries[5]+entries[6]+entries[7]+entries[8]+entries[9]);
						}


					}

					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					dbr.close();
					dbh.close();
					return null;
				}
				
				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					p.dismiss();
					super.onPostExecute(result);
				}
			}
			new DB_create().execute();

		}
	}

}

class Dbhelp extends SQLiteOpenHelper{
	private static final String DATABASE_CREATE = "create table if not exists 'maalgaadim' (_id integer primary key autoincrement, name text not null, cat text not null, sub_cat text not null, mrp text not null, vat text not null, cost_price text not null, sell_price text not null, discount text not null, discount_percent text not null, description text not null);";
	private static final String DATABASE_CREATE1 = "create table if not exists 'cart' (_id integer primary key autoincrement, name text not null, mrp text not null );";

	public Dbhelp(Context context, String name) {
		super(context, name, null,1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

