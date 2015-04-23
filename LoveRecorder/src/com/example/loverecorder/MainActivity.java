package com.example.loverecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;




public class MainActivity extends Activity implements OnClickListener {
	private EditText username,password;
	private Button login;
	ImageButton stopMusic,startMusic;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        stopMusic=(ImageButton)findViewById(R.id.stop);
        stopMusic.setOnClickListener(this);
        startMusic=(ImageButton) findViewById(R.id.play);
        startMusic.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View arg0) {
    	 Intent serviceIntent=new Intent(this,MusicService.class);
    if(arg0.getId()==R.id.loginButton){
		if(MainActivity.this.username.getText().toString().equals("LYL")&&MainActivity.this.password.getText().toString().equals("111059"))
		{	
			
			Intent activityIntent =new Intent(this,SlideActivity.class);
			startActivity(activityIntent);
		}
		else
		{
			MainActivity.this.finish();
		}}
    else if(arg0.getId()==R.id.play)
    {	Log.d("tag", "start了");
    	startService(serviceIntent);
    }
    else if(arg0.getId()==R.id.stop)
    {	Log.d("tag", "start了");
    	stopService(serviceIntent);
    }
    	
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime) > 2000){  
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
                exitTime = System.currentTimeMillis();   
            } else {
                finish();
                System.exit(0);
            }
            return true;   
        }
        return super.onKeyDown(keyCode, event);
    }
	
}
