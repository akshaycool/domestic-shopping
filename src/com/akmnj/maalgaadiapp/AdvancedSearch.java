package com.akmnj.maalgaadiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdvancedSearch extends Activity {
EditText e1,e2;
String sort,cat;
Spinner s1,s2;
String categ[]={this.getString(R.string.prod1),this.getString(R.string.prod2),this.getString(R.string.prod3),this.getString(R.string.prod4),this.getString(R.string.prod5),this.getString(R.string.prod6),this.getString(R.string.prod7),this.getString(R.string.prod8),this.getString(R.string.prod9),this.getString(R.string.prod10)};
String sort_by[]={"Ascending Name","Descending Name","Ascending Price","Descending Price","Descending Price"};
String category;
int pos;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_search);
		e1=(EditText)findViewById(R.id.min);
		e2=(EditText)findViewById(R.id.max);
		s1=(Spinner)findViewById(R.id.sortspinner);
		s2=(Spinner)findViewById(R.id.catspinner);
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,categ);
		ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,sort_by);
		s1.setAdapter(adapter);
		s2.setAdapter(adapter1);
		s1.setOnItemClickListener(new OnItemClickListener() {
        
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView t1=(TextView)arg1;
				category=t1.getText().toString();
				
			}
		});
		s2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				  pos=arg2;
				  Toast.makeText(getApplicationContext(), pos,Toast.LENGTH_SHORT).show();
			}
		});
		Intent i=new Intent(this,Search.class);
		i.putExtra("min_prc",e1.getText().toString());
		i.putExtra("max_prc",e2.getText().toString());
		i.putExtra("category",category);
		i.putExtra("sort",pos);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced_search, menu);
		return true;
	}

}                              