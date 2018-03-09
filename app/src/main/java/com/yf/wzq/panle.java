package com.yf.wzq;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import java.util.*;

public class panle extends View

{
	float pointx,pointy;
	ArrayList<Float> ptsx;
	ArrayList<Float> ptsy;
    
	Paint mpaint,rectpaint;
	
	
	public panle(Context context,AttributeSet set)
	{
		super(context,set);
		init();
	}

	public void init()
	{

		ptsx=new ArrayList<Float>();
		ptsy=new ArrayList<Float>();

		mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		mpaint.setColor(Color.BLACK);
		mpaint.setStyle(Paint.Style.FILL);
		
		

		rectpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		rectpaint.setColor(Color.WHITE);
		rectpaint.setStyle(Paint.Style.FILL);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
 
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
		
		
		float startx=0;
		float starty=0;
		float stopx=getWidth();
		float stopxx=0;
		float stopy=0;
		float stopyy=getHeight();
		float step=0;
		float points[]=new float [] {0,0,getWidth(),0};
		
		canvas.translate(0,0);
		for(int i=0;i<16;i++)
		{
			canvas.drawLine(startx,starty+step,stopx,starty+step,mpaint);
			
			step+=100;
		}
		 step=0;
		for(int j=0;j<15;j++)
		{
			canvas.drawLine(startx+step,starty,startx+step,stopyy,mpaint);
			step+=100;
		}
        
		if(ptsx.contains(pointx)&&ptsy.contains(pointy)){
			canvas.drawRect(pointx-50,pointy+50,pointx+50,pointy-50,mpaint);	
		}else{
			canvas.drawRect(pointx-50,pointy+50,pointx+50,pointy-50,rectpaint);	
		}
		
		
		for(int k=0;k<ptsx.size();k++)
		{
			float lx=ptsx.get(k)-50;
			float ly=ptsy.get(k)-50;
			canvas.drawRect(lx,ly+100,lx+100,ly,mpaint);
			
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		
		int actioncode=event.getAction();
		if(actioncode==MotionEvent.ACTION_DOWN)
		{
			pointx=event.getX()-event.getX()%100+50;
			pointy=event.getY()-event.getY()%100+50;	
				
		}
		
        else if(actioncode==MotionEvent.ACTION_UP){
			    if(ptsx.contains(pointx)&&ptsy.contains(pointy)){
					ptsx.remove(pointx);
					ptsy.remove(pointy);
				}else{

					ptsx.add(pointx);
					ptsy.add(pointy);
				}
				
			invalidate();
		}
		
		return true;
	}
	 
	
	
}
