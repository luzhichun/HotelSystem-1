package com.hotel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HotelSystemn extends Activity {
    /** Called when the activity is first created. */
	private Button loginBtn;
	private Button cancelBtn;
	private Button registerBtn;
	private SQLiteDatabase db;
	private EditText login;
	private EditText pwd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        MySqliteHelper mySqliteHelper = new MySqliteHelper(this, "hotelSys.db", null, 1);
        db = mySqliteHelper.getReadableDatabase();
        
        
        loginBtn = (Button) findViewById(R.id.loginBtn);
    	loginBtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				login = (EditText) findViewById(R.id.usernameEditText);
				pwd = (EditText)findViewById(R.id.pwdEditText);
				String name = login.getText().toString();
				String password = pwd.getText().toString();
				Cursor query = db.query("user", new String[]{"username","pwd"},"username=? and pwd=?", new String[]{name,password}, null, null, null);
				if (query.moveToFirst()) {
					Intent intent = new Intent();
					intent.setClass(HotelSystemn.this, EnterIntoActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "输入有误,请重新输入...", Toast.LENGTH_SHORT).show();
				} 
			}
    	});
    	
    	cancelBtn = (Button) findViewById(R.id.cancelBtn);
    	cancelBtn.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View v) {
				login = (EditText) findViewById(R.id.usernameEditText);
				pwd = (EditText)findViewById(R.id.pwdEditText);
				login.setText("");
				pwd.setText("");
			}
    		
    	});
    	
        //register button click
    	registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new Button.OnClickListener(){
		public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HotelSystemn.this, RegisterActivity.class);
				startActivity(intent);
			}
        });
        
    }
}