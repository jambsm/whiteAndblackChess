package com.yf.wzq;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import java.util.*;
import android.widget.*;
import java.lang.annotation.*;
import android.os.*;

public class wzqpanle extends View
{
	
	public float panelw;
	public float panelstart,panelstop;
	
	public float dist;
	public float step;
	Paint mpaint;
	
	public ArrayList<Point> whitelist,blacklist;
	Bitmap whiteChess,blackChess;
	
	float pointx,pointy;
	
	boolean isWhite=true;
	boolean isgameover=false;
	public wzqpanle(Context context,AttributeSet attr){
		super(context,attr);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
		canvas.translate(0,0);
		drawpanle(canvas);
		drawchess(canvas);
		isGameOver();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		// TODO: Implement this method
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	
	//触摸事件，触摸弹起时记录棋子位置
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
	if(isgameover){
		return false;
	}
		 if(event.getAction()==MotionEvent.ACTION_UP){
			pointx=event.getX()-event.getX()%100;
			pointy=event.getY()-event.getY()%100;
			Point p=new Point((int)pointx,(int)pointy);
			if(whitelist.contains(p)||blacklist.contains(p)){
				return false;
			}
			/*
			 if(isWhite){
				 whitelist.add(p);
			 }else{
				 blacklist.add(p);
			 }
			 */
			 whitelist.add(p);
			 
			 new Thread(){
				 public void run(){
					 try
					 {
						 sleep(500);
					 }
					 catch (InterruptedException e)
					 {
						 
						 e.printStackTrace();
					 }
				 }
			 }.start();
			 computerStep(p);
			 invalidate();
			 //isWhite=!isWhite;
		}
		
		return true;
	}
	
	//初始化各种变量
	public void init()
	{
		mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
		mpaint.setColor(Color.BLACK);
		mpaint.setStyle(Paint.Style.FILL);
		Bitmap tmpwhite,tmpblack;
		
		
		int oldwidth,oldheight;
		
		
		tmpblack=BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
		tmpwhite=BitmapFactory.decodeResource(getResources(),R.drawable.stone_w2);
		
		whiteChess=scaleBitmap(tmpwhite,(float)0.5);
		blackChess=scaleBitmap(tmpblack,(float)0.5);
		
		whitelist=new ArrayList<Point>();
		blacklist=new ArrayList<Point>();
		
		
	}
	
	//缩放棋子大小
   public Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
	
	//绘制棋子
	public void drawchess(Canvas canvas){
		for(int i=0;i<whitelist.size();i++){
			canvas.drawBitmap(whiteChess,whitelist.get(i).x-50,whitelist.get(i).y-50,mpaint);
		}
		
		for(int i=0;i<blacklist.size();i++){
			canvas.drawBitmap(blackChess,blacklist.get(i).x-50,blacklist.get(i).y-50,mpaint);
		}
		
		
	}
	
	
	//绘制棋盘
	public void drawpanle(Canvas canvas)
	{ 
	    panelw=getWidth();
		panelstart=0;
		
		step=0;
	for(int i=0;i<getHeight()/100+1;i++){
		canvas.drawLine(0,panelstart+step,getWidth(),panelstart+step,mpaint);
		canvas.drawLine(step,0,step,getHeight(),mpaint);
		step=step+100;
		
		}
		
	}
	
	//电脑走棋
	public void computerStep(Point point){
		
			
		if(!Havefour(point)){
				if(!HaveThree(point)){
					if(!HaveTwo(point)){
						HaveOne(point);
					}
				}
			}
			
			
		
	
	}
	
	
	public boolean Havefour(Point point){
		if(checkh(point,4))
			return true;
		if(checkv(point,4))
			return true;
		if(checkr(point,4))
			return true;
		if(checkl(point,4))
			return true;
		return false;
		
	}
	
	//行扫描，扫描每一个棋子左右两边是否存在三个棋子
	public boolean checkh(Point point,int num){
			if(checkhleft(point,num))
			{
				int x=point.x;
				int y=point.y;
				Point p=new Point(x+num*100,y);
				Point p1=new Point(x-100,y);
				if(!whitelist.contains(p)&&!blacklist.contains(p)){
					blacklist.add(p);
				}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
					blacklist.add(p1);
					
				}
				return true;
			}else if(checkhright(point,num)){
				int x=point.x;
				int y=point.y;
				Point p=new Point(x-num*100,y);
				Point p1=new Point(x+100,y);
				if(!whitelist.contains(p)&&!blacklist.contains(p)){
					blacklist.add(p);
				}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
					blacklist.add(p1);
				}
				return true;
			}else if(checkh_center_left(point,num)){
				int x=point.x;
				int y=point.y;
		
				Point p=new Point(x-100,y);
				if(!whitelist.contains(p)&&!blacklist.contains(p)){
					blacklist.add(p);
					
				}
				
				return true;
			}else if(checkh_center_right(point,num)){
				
				int x=point.x;
				int y=point.y;
				Point p=new Point(x+100,y);
				
				if(!whitelist.contains(p)&&!blacklist.contains(p)){
					blacklist.add(p);
					
				}
				return true;
			}
			return false;
	}
	
	
	public boolean checkhleft(Point point ,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		//判断横行有没有四个
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x+i*100,y))){
				count++;
			}
		}
		if(count==num)
			return true;
			
		return false;
	}
	
	public boolean checkhright(Point point,int num){
        int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x-i*100,y))){
				count++;
				
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	
	public boolean checkh_center_left(Point point,int num){
		
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		if(whitelist.contains(new Point(x-200,y))&&whitelist.contains(new Point(x-300,y))){
			return true;
		}
		/*for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x-i*100,y))){
				count++;
             }
		}
		if(whitelist.contains(new Point(x+100,y)))
			count++;
		if(count==num)
			return true;*/
		return false;	
	}
	
	public boolean checkh_center_right(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		if(whitelist.contains(new Point(x+200,y))&&whitelist.contains(new Point(x+300,y))){
			return true;
		}
		/*for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x+i*100,y))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x-100,y)))
			count++;
		if(count==num)
			return true;*/
		return false;
	}
	
	
	//列扫描
	public boolean checkv(Point point,int num){
		if(checkvup(point,num))
		{
			int x=point.x;
			int y=point.y;
			Point p=new Point(x,y-num*100);
			Point p1=new Point(x,y+100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
				blacklist.add(p1);
			}
			return true;
		}else if(checkvdown(point,num)){
			int x=point.x;
			int y=point.y;
			Point p=new Point(x,y+num*100);
			Point p1=new Point(x,y-100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
				blacklist.add(p1);
			}
			return true;
		}else if(checkv_center_up(point,num)){
			int x=point.x;
			int y=point.y;
			
			Point p=new Point(x,y-100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
				return true;
			}
			
			
		}else if(checkv_center_down(point,num)){
			int x=point.x;
			int y=point.y;
			Point p=new Point(x,y+(num-1)*100);
			Point p1=new Point(x,y+100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);

				return true;
			}
		}
		return false;
	}
	
	public boolean checkvup(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x,y-i*100))){
				count++;
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	public boolean checkvdown(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x,y+i*100))){
				count++;
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	public boolean checkv_center_up(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		/*for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x,y-i*100))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x,y+100)))
			count++;
		if(count==num)
			return true;*/
		if(whitelist.contains(new Point(x,y-200))&&whitelist.contains(new Point(x,y-300))){
			return true;
		}
		return false;
	}
	
	public boolean checkv_center_down(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		/*
		for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x,y+i*100))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x,y-100)))
			count++;
		if(count==num)
			return true;*/
		if(whitelist.contains(new Point(x+200,y))&&whitelist.contains(new Point(x+300,y))){
			return true;
		}
		return false;
	}
	
	
	
	
	public boolean checkl(Point point,int num){
		
		if(checklup(point,num))
		{
			int x=point.x;
			int y=point.y;
			Point p=new Point(x+num*100,y-num*100);
			Point p1=new Point(x-100,y+100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
				blacklist.add(p1);
			}
			return true;
		}else if(checkldown(point,num)){
			int x=point.x;
			int y=point.y;
			Point p=new Point(x-num*100,y+num*100);
			Point p1=new Point(x+100,y-100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
				blacklist.add(p1);
			}
			return true;
		}else if(checkl_center_left(point,num)){
			int x=point.x;
			int y=point.y;
			
			Point p=new Point(x-100,y+100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}
			return true;
		}else if(checkl_center_right(point,num)){
			int x=point.x;
			int y=point.y;
			
			Point p=new Point(x+100,y-100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}
			return true;
		}
		return false;
	}
	 
	public boolean checklup(Point point ,int num){
		
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x+i*100,y-i*100))){
				count++;
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	public boolean checkldown(Point point ,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x-i*100,y+i*100))){
				count++;
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	public boolean checkl_center_right(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		/*
		for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x+i*100,y-i*100))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x-100,y+100)))
			count++;
		if(count==num)
			return true;*/
		if(whitelist.contains(new Point(x+200,y-200))&&whitelist.contains(new Point(x+300,y-300))){
			return true;
		}
		return false;
	}
	
	public boolean checkl_center_left(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;/*
		for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x-i*100,y+i*100))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x+100,y-100)))
			count++;
		if(count==num)
			return true;*/
			
		if(whitelist.contains(new Point(x-200,y+200))&&whitelist.contains(new Point(x-300,y+300))){
			return true;
		}
		return false;
	}
	
	public boolean checkr(Point point,int num){
		
		if(checkrup(point,num))
		{
			int x=point.x;
			int y=point.y;
			Point p=new Point(x-num*100,y-num*100);
			Point p1=new Point(x+100,y+100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
				blacklist.add(p1);
			}
			return true;
		}else if(checkrdown(point,num)){
			int x=point.x;
			int y=point.y;
			Point p=new Point(x+num*100,y+num*100);
			Point p1=new Point(x-200,y-200);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}else if(!whitelist.contains(p1)&&!blacklist.contains(p1)){
				blacklist.add(p1);
			}
			return true;
		}else if(checkr_center_left(point,num)){
			int x=point.x;
			int y=point.y;
			
			Point p=new Point(x-100,y-100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}
			return true;
		}else if(checkr_center_right(point,num)){
			int x=point.x;
			int y=point.y;
			
			Point p=new Point(x+100,y+100);
			if(!whitelist.contains(p)&&!blacklist.contains(p)){
				blacklist.add(p);
			}
			return true;
		}
		return false;
	}
	
	public boolean checkrup(Point point ,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x-i*100,y-i*100))){
				count++;
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	public boolean checkrdown(Point point ,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;
		for(int i=0;i<num;i++){
			if(whitelist.contains(new Point(x+i*100,y+i*100))){
				count++;
			}
		}
		if(count==num)
			return true;
		return false;
	}
	
	public boolean checkr_center_left(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;/*
		for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x-i*100,y-i*100))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x+100,y+100)))
			count++;
		if(count==num)
			return true;*/

		if(whitelist.contains(new Point(x-200,y-200))&&whitelist.contains(new Point(x-300,y-300))){
			return true;
		}
		return false;
	}
	
	public boolean checkr_center_right(Point point,int num){
		int x,y;
		x=point.x;
		y=point.y;
		int count=0;/*
		for(int i=0;i<num-1;i++){
			if(whitelist.contains(new Point(x+i*100,y+i*100))){
				count++;
			}
		}
		if(whitelist.contains(new Point(x-100,y-100)))
			count++;
		if(count==num)
			return true;*/

		if(whitelist.contains(new Point(x+200,y+200))&&whitelist.contains(new Point(x+300,y+300))){
			return true;
		}
		return false;
	}
	
	
	public boolean HaveThree(Point point){
		if(checkh(point,3))
			return true;
		if(checkv(point,3))
			return true;
		if(checkr(point,3))
			return true;
		if(checkl(point,3))
			return true;
		return false;
	}
	
	public boolean HaveTwo(Point point){
		if(checkh(point,2))
			return true;
		if(checkv(point,2))
			return true;
		if(checkr(point,2))
			return true;
		if(checkl(point,2))
			return true;
		return false;
	}
	
	public boolean HaveOne(Point point){
		if(checkh(point,1))
			return true;
		if(checkv(point,1))
			return true;
		if(checkr(point,1))
			return true;
		if(checkl(point,1))
			return true;
		return false;
	}
	
	
	//判断胜负............
	public void isGameOver(){
	boolean whiteWin,blackWin;
		whiteWin=checkChess(whitelist);
	    blackWin=checkChess(blacklist);
		if(whiteWin||blackWin){
			isgameover=true;
			String msg=null;
			if(whiteWin)
				msg="白棋胜";
			else if(blackWin)
				msg="黑棋胜";
			Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
		}
		
	}
	
	public boolean checkChess(ArrayList<Point> pointlist){
		boolean win=false;
		for(Point point:pointlist){
			win=checkhorizontal(point,pointlist);
			if(win)return true;
			win=checkvertical(point,pointlist);
			if(win)return true;
			win=checkleft(point,pointlist);
			if(win)return true;
			win=chrckright(point,pointlist);
			if(win)return true;
		}
		return false;
	}
	
	public boolean checkhorizontal(Point p,ArrayList<Point> arr){
		
		int count = 1;
		int x,y;
		x=p.x;
		y=p.y;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x-i*100,y))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x+i*100,y))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}
	
	public boolean checkvertical(Point p,ArrayList<Point> arr){
		int count = 1;
		int x,y;
		x=p.x;
		y=p.y;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x,y-i*100))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x,y+i*100))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}
	
	public boolean checkleft(Point p,ArrayList<Point> arr){
		int count = 1;
		int x,y;
		x=p.x;
		y=p.y;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x-i*100,y-i*100))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x+i*100,y+i*100))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
		
	}
	public boolean chrckright(Point p,ArrayList<Point> arr){
		int count = 1;
		int x,y;
		x=p.x;
		y=p.y;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x-i*100,y+i*100))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (arr.contains(new Point(x+i*100,y-i*100))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}
	
	//..........判断获胜可能
	
}
