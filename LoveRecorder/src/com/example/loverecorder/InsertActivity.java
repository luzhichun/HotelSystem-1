package com.example.loverecorder;


import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;


public class InsertActivity extends Activity implements OnClickListener {
	private String addText,timesText,dateText=null,recallText;
	private EditText addressEdit,timesEdit,recallEdit;
	private Button saveButton;
    private	DatePicker datePicker;
  //  public  MySQLiteOpenHelper mySqliteHelper= new MySQLiteOpenHelper(this, "love.db", 1); 
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert);
		
		Log.d("TAG","!!!!!!!!!!!!!!!!");
		
		addressEdit=(EditText) findViewById(R.id.addressEdit);
		timesEdit=(EditText) findViewById(R.id.timesEdit);
		recallEdit=(EditText) findViewById(R.id.recallEdit);
		saveButton=(Button) findViewById(R.id.save);
		datePicker=(DatePicker) findViewById(R.id.datePicker);
		saveButton.setOnClickListener(this);
		
		datePicker.init(2014, 10, 10, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
			dateText=Integer.toString(year)+"��"+Integer.toString(monthOfYear+1)+"��"+Integer.toString(dayOfMonth)+"��";
				
			}
		});
	}
	@Override
	public void onClick(View arg0) {
		 SQLiteDatabase db=openOrCreateDatabase("Love.db",MODE_APPEND, null);
		addText=addressEdit.getText().toString();
		timesText=timesEdit.getText().toString();
		recallText=recallEdit.getText().toString();
		if(dateText==null)
		Toast.makeText(getApplicationContext(),"öö����û��ѡ�����ڣ������ԣ�", Toast.LENGTH_SHORT).show();
		else
		{	
		 try{db.execSQL("insert into mytab(date,address,times,recall)values(?,?,?,?)",new String[]{dateText,addText,timesText,recallText});
		 	Toast.makeText(getApplicationContext(),"��ϲöö������д��ɹ���", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e)
			{	Toast.makeText(getApplicationContext(),"öö������д��ʧ���ˣ������µ�������ύ��", Toast.LENGTH_SHORT).show();
				String createMytab="create table mytab(_id integer primary key autoincrement,date varchar(30),address varchar(30),times varchar(10),recall varchar(255))";
				db.execSQL(createMytab);
				
			}
			
				}
		if(db!=null&&db.isOpen())
		{
			db.close();
		}
		
				}
	
}
