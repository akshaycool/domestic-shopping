package com.akmnj.maalgaadiapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;





public class MenuActivity extends SherlockActivity implements ISideNavigationCallback{
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode==2){
			sideNavigationView.showMenu();
			
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static final String EXTRA_TITLE = "com.devspark.sidenavigation.sample.extra.MTGOBJECT";
    public static final String EXTRA_RESOURCE_ID = "com.devspark.sidenavigation.sample.extra.RESOURCE_ID";
    public static final String EXTRA_MODE = "com.devspark.sidenavigation.sample.extra.MODE";
    private String strips[]={"Browse by Category","About Us","CheckOut","Search"};
    private ImageView icon;
    static Context con;
    private SideNavigationView sideNavigationView;
    private AlertDialog d1;
    String description=" MaalGaadi.in is one of its kind online grocery shop for you in Mumbai\n" +
    		"Order groceries online & have them delivered right at your doorstep\n" +
    		"We have an wide range of products popular and branded ones to choose from\n" +
    		"Shop what forms your daily needs at discounted rates\n" +
    		"AT YOUR DOORSTEP WHENEVER YOU SAY!!!";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
        setContentView(R.layout.activity_main);
       
        
        ListView itemlv=(ListView)findViewById(android.R.id.list);
        Myadapter adapter =new Myadapter(this,R.layout.list_1,strips);
        // ArrayAdapter<String> adapter=new ArrayAdapter<String>(MenuActivity.this,android.R.layout.simple_dropdown_item_1line,strips);
       itemlv.setAdapter(adapter);
       
       
       if(!Db.db_exists(this))
       {
    	   Log.e("MAAL", "before DB");
    	   Db.new_db(this);
    	   Log.e("MAAL", "after DB");
       }
       SharedPreferences prefs=this.getSharedPreferences("com.akmnj.maalgaadiapp", Context.MODE_PRIVATE);
       if(!prefs.contains("name")&&!prefs.contains("no")&&!prefs.contains("address"))
		{
			Toast.makeText(this,"There seems to be a problem with your registration. Please register yourself to use the app", Toast.LENGTH_LONG).show();
			Checkout.getReg(this).show();
		}
       itemlv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(arg0.getItemAtPosition(arg2).equals(strips[0]))
			{
			    //Toast.makeText(MenuActivity.this, "SHOP",Toast.LENGTH_SHORT).show();
				sideNavigationView.showMenu();
			}
			if(arg0.getItemAtPosition(arg2).equals(strips[1]))
			{     //Toast.makeText(MenuActivity.this, "ABOUT US",Toast.LENGTH_SHORT).show();
				  d1=new AlertDialog.Builder(MenuActivity.this).setTitle("About us").setMessage(description).create();
                  d1.show();
			}
			if(arg0.getItemAtPosition(arg2).equals(strips[2])){
				startActivity(new Intent(MenuActivity.this,Checkout.class));
				
			}
			if(arg0.getItemAtPosition(arg2).equals(strips[3])){
				startActivity(new Intent(MenuActivity.this,AdvancedSearch.class));
				
			}
		
			/*
			if(arg0.getItemAtPosition(arg2).equals(strips[4])){
				Dialog d=new Dialog(MenuActivity.this);
				LinearLayout ll=new LinearLayout(MenuActivity.this);
				ll.setOrientation(LinearLayout.VERTICAL);
				final EditText et=new EditText(MenuActivity.this);
				ll.addView(et);
				Button b=new Button(MenuActivity.this);
				b.setText("Go!");
				b.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent in=new Intent(con,Search.class);
						in.putExtra("src", et.getText().toString());
						startActivity(in);
					}
				});
				ll.addView(b);
				d.setTitle("Enter your search query here:");
				d.setContentView(ll);
				d.show();				
			}
			*/
			// d1.setTitle("About Us");
		}
	});
/*
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			TextView t1=(TextView)arg1;
			
			       
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});*/
        // icon = (ImageView) findViewById(android.R.id.icon);
        sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
        sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        sideNavigationView.setMenuClickCallback(this);

       /* if (getIntent().hasExtra(EXTRA_TITLE)) {
            String title = getIntent().getStringExtra(EXTRA_TITLE);
            int resId = getIntent().getIntExtra(EXTRA_RESOURCE_ID, 0);
            setTitle(title);
          //  icon.setImageResource(resId);
            sideNavigationView.setMode(getIntent().getIntExtra(EXTRA_MODE, 0) == 0 ? Mode.LEFT : Mode.RIGHT);
        }*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent().getBooleanExtra("frm_img", false))
        {
        	sideNavigationView.showMenu();
        }
    }

    
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.main_menu, menu);
        if (sideNavigationView.getMode() == Mode.RIGHT) {
            menu.findItem(R.id.mode_right).setChecked(true);
        } else {
            menu.findItem(R.id.mode_left).setChecked(true);
        }
       
       
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sideNavigationView.toggleMenu();
              //  Toast.makeText(con, "NAvigate selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mode_left:
                item.setChecked(true);
                sideNavigationView.setMode(Mode.LEFT);
                //Toast.makeText(con, "NAvigate selected1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mode_right:
                item.setChecked(true);
                sideNavigationView.setMode(Mode.RIGHT);
                //Toast.makeText(con, "NAvigate selected2",Toast.LENGTH_SHORT).show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onSideNavigationItemClick(int itemId) {
    	if(Db.ex_code==0){
    	switch (itemId) {
            case R.id.side_navigation_menu_item1:
            	//Toast.makeText(MenuActivity.this, "HELLO",Toast.LENGTH_SHORT).show();
                invokeActivity(getString(R.string.prod1));//, R.drawable.ic_android1);
                break;

            case R.id.side_navigation_menu_item2:
                invokeActivity(getString(R.string.prod2));//, R.drawable.ic_android2);
                break;

            case R.id.side_navigation_menu_item3:
                invokeActivity(getString(R.string.prod3));//, R.drawable.ic_android3);
                break;

            case R.id.side_navigation_menu_item4:
                invokeActivity(getString(R.string.prod4));//, R.drawable.ic_android4);
                break;

            case R.id.side_navigation_menu_item5:
                invokeActivity(getString(R.string.prod5));//, R.drawable.ic_android5);
                break;
            case R.id.side_navigation_menu_item6:
            	 invokeActivity(getString(R.string.prod6));
            	 break;
            	 //, R.drawable.ic_android5);
            case R.id.side_navigation_menu_item7:
           	 invokeActivity(getString(R.string.prod7));
           	 break;
           	 //, R.drawable.ic_android5);
            case R.id.side_navigation_menu_item8:
           	 invokeActivity(getString(R.string.prod8));//, R.drawable.ic_android5);
           	 break;
            case R.id.side_navigation_menu_item9:
           	 invokeActivity(getString(R.string.prod9));
           	 break;
           	 // R.drawable.ic_android5);
            case R.id.side_navigation_menu_item10:
           	 invokeActivity(getString(R.string.prod10));
           	 break;
           	 //, R.drawable.ic_android5);
            default:
                return;
        }
    }
    	else
			Toast.makeText(this, "Please wait. DB downloading", Toast.LENGTH_LONG).show();	 	
        finish();
    }

    @Override
    public void onBackPressed() {
        // hide menu if it shown
    	//Toast.makeText(MenuActivity.this, "HELLO",Toast.LENGTH_SHORT).show();
        if (sideNavigationView.isShown()) {
            sideNavigationView.hideMenu();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Start activity from SideNavigation.
     * 
     * @param title title of Activity
     * @param resId resource if of background image
     */
    private void invokeActivity(String title) {
        Intent intent = new Intent(this,SubCategorylist.class);
     intent.putExtra(EXTRA_TITLE, title);
      //  intent.putExtra(EXTRA_RESOURCE_ID, resId);
        //intent.putExtra(EXTRA_MODE, sideNavigationView.getMode() == Mode.LEFT ? 0 : 1);

        // all of the other activities on top of it will be closed and this
        // Intent will be delivered to the (now on top) old activity as a
        // new Intent.
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // no animation of transition
        overridePendingTransition(0, 0);
    }
/*	
public void addTransc(View v){
	startActivity(new Intent(this,TranscaddActivity.class));
*/
    
   public class Myadapter extends ArrayAdapter<String>{
	   
	   /**
	 * @param context
	 * @param resource
	 * @param objects
	 */
	 Context c;  
	 String [] items =new String[100];
	public Myadapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		this.c=context;
		items=objects;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout l1=(LinearLayout)convertView.inflate(c,R.layout.list_1,null);
		TextView t1=(TextView)l1.findViewById(R.id.text1);
		t1.setText(items[position]);
		return l1;
	}
   }
	//Myadapter(Context con,String[] items){
		   
		   
   }
	   
	   
 
    
    
    
    
    
    
    
    
    
    
    
    
   	

	
	
	
	
	
	

	
	
	
	
	
	
	
	

