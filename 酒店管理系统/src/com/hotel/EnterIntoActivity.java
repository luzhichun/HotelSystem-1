package com.hotel;

import java.security.PublicKey;

import android.app.TabActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class EnterIntoActivity extends TabActivity {

	private SQLiteDatabase db;
	private EditText nameEditText;
	private RadioButton radioMan;
	private RadioButton radioWoman;
	private EditText idCardEditText;
	private EditText timeInEditText;
	private EditText timeSumEditText;
	private EditText roomNumEditText;
	private EditText moneyEditText;
	private Button btn01;
	private Button btn02;
	private String result="";
	private String SearchResult="";
	private Button search;
	private TextView resultTextView;
	
	private EditText searchEditText;
	
	private Button dropBtn;
	private EditText usernameEditText;
	private EditText roomNum01EditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final MySqliteHelper mySqliteHelper = new MySqliteHelper(this, "hotelSys.db", null, 1);
		db=mySqliteHelper.getWritableDatabase();
		
		//set tab
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.manage, tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("入住",null).setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("查询",null).setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("退房",null).setContent(R.id.tab3));
		
		
		btn01 = (Button) findViewById(R.id.btn01);
		btn01.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) { 
				try {
					nameEditText = (EditText) findViewById(R.id.nameEditText); 
					radioMan = (RadioButton) findViewById(R.id.man);
					radioWoman = (RadioButton) findViewById(R.id.woman);
					idCardEditText = (EditText)findViewById(R.id.idCardEditText);
					timeInEditText = (EditText)findViewById(R.id.timeInEditText);
					timeSumEditText = (EditText)findViewById(R.id.timeSumEditText);
					roomNumEditText = (EditText)findViewById(R.id.roomNumEditText);
					moneyEditText = (EditText)findViewById(R.id.moneyEditText);
					String name = nameEditText.getText().toString();
					String sex = "";
					if(radioMan.isChecked()){
						sex="man";
					}else if(radioWoman.isChecked()){
						sex="woman";
					}
					String idCard = idCardEditText.getText().toString();
					String timeIn = timeInEditText.getText().toString();
					String timeSum = timeSumEditText.getText().toString();
					String roomNum = roomNumEditText.getText().toString();
					String money = moneyEditText.getText().toString();
					
					db=mySqliteHelper.getReadableDatabase();
					Cursor query = db.query("hotel", new String[]{"roomNum","name","sex","idCard","timeIn","timeSum","money"}, "roomNum=?", 
							new String[]{roomNum}, null, null, "_id asc");
					if(!query.moveToFirst()){ 
						db.execSQL("insert into hotel(name,sex,idCard,timeIn,timeSum,roomNum,money) values(?,?,?,?,?,?,?)",
								new String[]{name,sex,idCard,timeIn,timeSum,roomNum,money});
							Toast.makeText(getApplicationContext(),"插入成功", Toast.LENGTH_SHORT).show();
							
						clean();
					}
					
					else{
						Toast.makeText(getApplicationContext(),"房间已经有人住...", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				db = mySqliteHelper.getReadableDatabase();
				Cursor query = db.query("hotel", new String[]{"name"}, null, null, null, null, "_id asc");
				for (query.moveToFirst();!(query.isAfterLast()); query.moveToNext()) {
					result = result+query.getString(query.getColumnIndex("name"));
				}
				query.close();
			}
			
		});
		
		
		//search 
		
		search = (Button) findViewById(R.id.searchBtn);
		search.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				resultTextView = (TextView) findViewById(R.id.resultTestView);
				searchEditText = (EditText) findViewById(R.id.searchEditText);
				String roomNum = searchEditText.getText().toString();
				db=mySqliteHelper.getReadableDatabase();
				Cursor query01 = db.query("hotel", new String[]{"roomNum","name","sex","idCard","timeIn","timeSum","money"}, "roomNum=?", new String[]{roomNum}, null, null, "_id asc");
				if(query01.moveToFirst()){
					for (query01.moveToFirst(); !query01.isAfterLast(); query01.moveToNext()) {
						SearchResult +="房间号："+ query01.getString(query01.getColumnIndex("roomNum"))+"\n";
						SearchResult +="姓　名："+ query01.getString(query01.getColumnIndex("name"))+"\n";
						SearchResult +="性　别："+ query01.getString(query01.getColumnIndex("sex"))+"\n";
						SearchResult +="身份证："+ query01.getString(query01.getColumnIndex("idCard"))+"\n";
						SearchResult +="入住时间："+ query01.getString(query01.getColumnIndex("timeIn"))+"\n";
						SearchResult +="入住天数："+ query01.getString(query01.getColumnIndex("timeSum"))+"\n";
						SearchResult +="总钱数："+ query01.getString(query01.getColumnIndex("money"));
					}
				}else{
					Toast.makeText(getApplicationContext(),"没有相关信息", Toast.LENGTH_SHORT).show();
				}
				
				query01.close();
				resultTextView.setText(SearchResult);
				SearchResult="";
			}
			
		});
		
		//退房
		dropBtn = (Button) findViewById(R.id.dropBtn);
		dropBtn.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				usernameEditText = (EditText) findViewById(R.id.dropUsernameEditText);
				roomNum01EditText = (EditText) findViewById(R.id.dropRoomNumEditText);
				String username = usernameEditText.getText().toString();
				String roomNum = roomNum01EditText.getText().toString();
				Cursor query=null;
					try {
						 query = db.query("hotel", new String[]{"_id"},"name=? and roomNum=?" , new String[]{username,roomNum}, null, null, null);
						if(query.moveToFirst()){
							db.delete("hotel", " name=? and roomNum=?", new String[]{username,roomNum});
							Toast.makeText(getApplicationContext(),"删除成功", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(),"数据不存在", Toast.LENGTH_SHORT).show();
						}
						
					} catch (Exception e) {
						query.close();
						e.printStackTrace();
					}
			
			}
			
		});
		btn02 = (Button) findViewById(R.id.btn02);
		btn02.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				clean();
			}
			
		});
		
	}
	//清空
	public void clean(){
		nameEditText = (EditText) findViewById(R.id.nameEditText); 
		radioMan = (RadioButton) findViewById(R.id.man);
		radioWoman = (RadioButton) findViewById(R.id.woman);
		idCardEditText = (EditText)findViewById(R.id.idCardEditText);
		timeInEditText = (EditText)findViewById(R.id.timeInEditText);
		timeSumEditText = (EditText)findViewById(R.id.timeSumEditText);
		roomNumEditText = (EditText)findViewById(R.id.roomNumEditText);
		moneyEditText = (EditText)findViewById(R.id.moneyEditText);
		nameEditText.setText("");
		radioWoman.setChecked(true);
		idCardEditText.setText("");
		timeInEditText.setText("");
		timeSumEditText.setText("");
		roomNumEditText.setText("");
		moneyEditText.setText("");
		
		
	}
}
