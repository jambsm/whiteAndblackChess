package com.yf.wzq;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;

public class MainActivity extends Activity 
{
    Button startBtn,setBtn;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		startBtn=(Button)findViewById(R.id.start);
		setBtn=(Button)findViewById(R.id.set);
		startBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Intent intent=new Intent(MainActivity.this,game.class);
					startActivity(intent);
				}

		
		});
		
		
		setBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Intent intent=new Intent(MainActivity.this,SettingActivity.class);
					startActivity(intent);
				}
				
	
		});
		
    }
}
