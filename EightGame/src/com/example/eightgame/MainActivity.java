package com.example.eightgame;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.android.yypService;

import comclass.ImageButton1;
import cutimg.ImagePiece;
import cutimg.ImageSplitter;
import eightnum.Main;
import adapter.DrawAdapter;
import android.R.anim;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ScrollView scrollView;
	ListView listView;
	DrawAdapter adapter;
	ArrayList<Drawable[]> drawablesList;
	private static int RESULT_LOAD_IMAGE = 1;
	Myhandler2 myhandler2=new Myhandler2();
	Myhandler myhandler=new Myhandler();
	ProgressDialog progressDialog;
	TextView textView;
	GameDb gameDb;
	SQLiteDatabase db;
	Button bu1,bu2,bu3,bu4;
	String[] gogogo;
	int goindex;
//	int[] img=new int[10];
	int startind=-1;
	ImageButton1[] button=new ImageButton1[10];
	protected void onStop() {  
	    // TODO Auto-generated method stub  
	    Intent intent = new Intent(MainActivity.this,yypService.class);  
	    stopService(intent);  
	    super.onStop();  
	}  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int[] st={R.drawable.js_01,R.drawable.a1_01,R.drawable.b_01,R.drawable.c_01,R.drawable.d_01,R.drawable.e_01,R.drawable.f_01,R.drawable.g_01,R.drawable.h_01,R.drawable.brock_01};
		drawablesList=buildDrawableList(st);
		adapter=new DrawAdapter(MainActivity.this, R.layout.image_item, drawablesList);
		listView=(ListView) findViewById(R.id.lv);
		listView.setAdapter(adapter);
		
		
		progressDialog=new ProgressDialog(MainActivity.this);
		progressDialog.setMessage("正在计算");
	//	progressDialog.setCancelable(true);
		gameDb=new GameDb(this, "gamedatabase", null, 1);
		db=gameDb.getWritableDatabase();
		
		textView=(TextView) findViewById(R.id.textView1);
		load();
		setbu11();
		setbu2();
		setbu3();
		setbu4();
		
		scrollView=(ScrollView) findViewById(R.id.scorll);
		listView.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                listView.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startind=position-1;
				load();
				random1();
				
			}
		});
		
//		Intent intent=new Intent(MainActivity.this, yypService.class);
//		startService(intent);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				  Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	 
	             startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
			
		/*	@Override
			public void onClick(View v) {
				
				Work1000times thread= new Work1000times();
				myhandler2.work1000times=thread;
				thread.start();
			}*/
		});
		

	}
	
	public void load()
	{
		Random random=new Random();
	//	int[] st={R.drawable.js_01,R.drawable.a1_01,R.drawable.b_01,R.drawable.c_01,R.drawable.d_01,R.drawable.e_01,R.drawable.f_01,R.drawable.g_01,R.drawable.h_01,R.drawable.brock_01};
		
		
		
		int start=0;
	/*	if(startind==-1)
		{
			int jj=random.nextInt(st.length);
			startind=jj;
			start=st[jj];
		}
		else 
		{
			startind=(startind+1)%st.length;
			start=st[startind];
		}*/
		startind++;
		if(startind>=drawablesList.size()) startind=0;
		
		
	/*	for(int i=1;i<=8;++i)
			img[i]=start+i-1;
		img[0]=R.drawable.a1_09;*/
		start=R.id.bu0;
		for(int i=0;i<=8;++i)
		{
			button[i]=(ImageButton1) findViewById((start+i));
			button[i].setOnClickListener(new ClickListen(button[i], i));
		}
		loadnum();
	}
	@SuppressLint("NewApi")
	public void loadnum()
	{
		for(int i=0;i<=7;++i)
		{
			button[i].setNum(i+1);
		//	button[i].setBackgroundResource(img[i+1]);
			button[i].setBackground(drawablesList.get(startind)[i+1]);
		}
		button[8].setNum(0);
		button[8].setBackgroundColor(Color.WHITE);
	}
	class ClickListen implements OnClickListener
	{
		ImageButton1 b;
		int index;
		int[][] move={{-1,0},{1,0},{0,-1},{0,1}};
		public ClickListen(ImageButton1 b,int index)
		{
			this.b=b;
			this.index=index;
		}
		
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			if(b.getNum()==0) return;
			int x=index/3+1,y=index%3+1;
			for(int i=0;i<4;++i)
			{
				int x1=x+move[i][0],y1=y+move[i][1];
				if(x1>=1&&x1<=3&&y1>=1&&y1<=3)
				{
					int xy=3*(x1-1)+y1-1;
					if(button[xy].getNum()==0)
					{
						int temp=b.getNum();
						b.setNum(0);
					/*	b.setBackgroundColor(Color.WHITE);
						button[xy].setNum(temp);
						button[xy].setBackgroundResource(img[temp]);*/
						Drawable tempdDrawable=b.getBackground();
						b.setBackgroundColor(Color.WHITE);
						button[xy].setBackground(tempdDrawable);
						button[xy].setNum(temp);
						
						return;
					}
				}
				
			}
			
		}
		
	}
	public void setbu2()
	{
		bu2=(Button) findViewById(R.id.button2);
		bu2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Main main=new Main();
				String string="";
				for(int i=0;i<=8;++i)
					string+=String.valueOf(button[i].getNum());
				
				Message message=new Message();
				message.what=1;
				myhandler.sendMessage(message);
				
				String result=GameDb.findWay(string, db);
				if(result==null)
				{
					Log.d("find","no");
					main.main1(string, "123456780");
					GameDb.insertWay(string, main.getGo(), db);
					result=main.getGo();
					
				}
				
			
				gogogo=result.split(" ");
				goindex=0;
				clean(goindex);
				message.what=2;
				myhandler.sendMessage(message);*/
				try{
				new WorkThread().start();
				}catch(Exception e)
				{
					Log.d("e",e.toString());
				}
			}
		});
		
	}
	@SuppressLint("NewApi")
	public void clean(int ini) 
	{
		for(int i=0;i<=8;++i)
		{
			button[i].setNum(Integer.parseInt(gogogo[ini].substring(i, i+1)));
			if(button[i].getNum()==0)
			{
				button[i].setBackgroundColor(Color.WHITE);
			}
			else
			{
		//		button[i].setBackgroundResource(img[button[i].getNum()]);
				button[i].setBackground(drawablesList.get(startind)[button[i].getNum()]);
			
			}
		}
	}
	public void setbu3()
	{
		bu3=(Button) findViewById(R.id.button3);
		bu3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(goindex-1<0) return;
				goindex--;
				clean(goindex);
				
			}
		});
	}
	public void setbu4()
	{
		bu4=(Button) findViewById(R.id.button4);
		bu4.setOnClickListener(new OnClickListener() {
				
				@Override
		public void onClick(View v) {
			if(gogogo==null) {
				Toast.makeText(getApplicationContext(), "要先按解决", Toast.LENGTH_SHORT).show();return;
			}
			if(goindex+1>gogogo.length-1) return;
		
			goindex++;
				
			clean(goindex);
			
				}
			});
	}
	public void setbu1()
	{
		bu1=(Button)findViewById(R.id.button1);
		bu1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Main main=new Main();
				while(true)
				{
					
					main.main1(random(), "123456780");
				
					if(!main.getGo().equals(""))
					{
						Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();;			
						gogogo=main.getGo().split(" ");
						goindex=0;
						clean(0);
						return;
						
					}
				}
				
			}
			public String random()
			{
				
				Random random=new Random();
				ArrayList<Integer> list=new ArrayList<Integer>();
				for(int i=0;i<=8;++i) list.add(i);
				String string="";
				for(int i=1;i<=9;++i)
				{
					int ind=random.nextInt(list.size());
					string+=String.valueOf(list.get(ind));
					list.remove(ind);
				}
				return string;
			}
		});
	}
	public void setbu11()
	{
		bu1=(Button) findViewById(R.id.button1);
		bu1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				load();
				random1();
				
			}
		});
	}
	@SuppressLint("NewApi")
	public void random1()
	{
		Random random=new Random();
		int ind=0;
		int[][] move={{-1,0},{1,0},{0,-1},{0,1}};

		for(int i=0;i<=8;++i)
			if(button[i].getNum()==0) 
			{
				ind=i;break;
			}
		for(int j=1;j<=10000;++j)
		{
			int x=ind/3+1,y=ind%3+1;
			int i=random.nextInt(4);
			int x1=x+move[i][0],y1=y+move[i][1];
			if(x1>=1&&x1<=3&&y1>=1&&y1<=3)
			{
				int xy=3*(x1-1)+y1-1;
				button[ind].setNum(button[xy].getNum());
			//	button[ind].setBackgroundResource(img[button[ind].getNum()]);
				button[ind].setBackground(drawablesList.get(startind)[button[ind].getNum()]);
				button[xy].setNum(0);
				button[xy].setBackgroundColor(Color.WHITE);
				ind=xy;
					
					
			}
			
				
		}
	}
	void ran()
	{
		load();
		random1();
	}
	void work()
	{
		Main main=new Main();
		String string="";
		for(int i=0;i<=8;++i)
			string+=String.valueOf(button[i].getNum());
//		TextView textView=(TextView) findViewById(R.id.textView1);
//		textView.setText("work");
		
		String result=GameDb.findWay(string, db);
		if(result==null)
		{
			main.main1(string, "123456780");
			GameDb.insertWay(string, main.getGo(), db);
			result=main.getGo();
		}
		
	
		gogogo=result.split(" ");
		goindex=0;
//		clean(goindex);
	}
	class Myhandler extends Handler
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				progressDialog.show();
				break;
			case 2:
				progressDialog.dismiss();
			default:
				break;
			}
		}
		
	}
	class WorkThread extends Thread{
		public void run()
		{
			Main main=new Main();
			String string="";
			for(int i=0;i<=8;++i)
				string+=String.valueOf(button[i].getNum());
			
			Message message=new Message();
			message.what=1;
			myhandler.sendMessage(message);
			
			String result=GameDb.findWay(string, db);
			if(result==null)
			{
				Log.d("find","no");
				main.main1(string, "123456780");
				GameDb.insertWay(string, main.getGo(), db);
				result=main.getGo();
				
			}
			
		
			gogogo=result.split(" ");
			goindex=0;
			clean(goindex);
			Message message2=new Message();
			message2.what=2;
			myhandler.sendMessage(message2);
		
		}
	}
	class Work1000times extends Thread
	{
		int count;
		@Override
		public void run()
		{
			for(count=1;count<=2000;++count)
			{
				Message message1=new Message();
				message1.what=1;
				myhandler2.sendMessage(message1);
			//	MainActivity.this.ran();
				MainActivity.this.work();
			//	textView.setText(String.valueOf(count));
				Log.d("xx", String.valueOf(count));
				
				Message message2=new Message();
				message2.what=2;
				myhandler2.sendMessage(message2);
			}
		}
		
		
	}
	class Myhandler2 extends Handler
	{
		Work1000times work1000times;
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try{
				switch (msg.what) {
				case 1:
					progressDialog.show();
					MainActivity.this.ran();
				//	MainActivity.this.work();
					break;
				case 2:
					progressDialog.dismiss();
					progressDialog.setMessage(String.valueOf(work1000times.count));
				case 3:
					
				default:
					break;
				}
			}catch(Exception e)
			{
				String ee=e.toString();
				System.out.print(ee);
			}
		}
		
		
	}
	
	public  ArrayList<Drawable[]> buildDrawableList(int[] st)
	{
		ArrayList<Drawable[]> list=new ArrayList<Drawable[]>();
		for(int i=0;i<st.length;++i)
		{
			Drawable[] drawable=new Drawable[9];
			for(int j=1;j<=8;++j)
			{
				drawable[j]=getResources().getDrawable(st[i]+j-1);
				
			}
			drawable[0]=getResources().getDrawable(st[i]+8);
			list.add(drawable);
		}
		return list;		
	    	
	}
	Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap  
	{  
	    int width = drawable.getIntrinsicWidth();// 取drawable的长宽  
	    int height = drawable.getIntrinsicHeight();  
	    Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式  
	    Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap  
	    Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布  
	    drawable.setBounds(0, 0, width, height);  
	    drawable.draw(canvas);// 把drawable内容画到画布中  
	    return bitmap;  
	}  
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
 
        
            Bitmap b=BitmapFactory.decodeFile(picturePath);
            
            List<ImagePiece> list=ImageSplitter.split(b, 3, 3);
           // imageView.setImageBitmap(list.get(0).bitmap);
            Drawable[] drawables=new Drawable[9];
            for(int i=1;i<=8;++i)
            	drawables[i]=new BitmapDrawable(list.get(i-1).bitmap);
            drawables[0]=new BitmapDrawable(list.get(8).bitmap);
            drawablesList.add(drawables);
            adapter.notifyDataSetChanged();
        }
 
    }
}
	
	

