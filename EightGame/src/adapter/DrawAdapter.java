package adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.eightgame.R;

import comclass.ImageButton1;
import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class DrawAdapter extends ArrayAdapter<Drawable[]>{

	int resourceId;
	public DrawAdapter(Context context, int resource, List<Drawable[]> objects) {
		super(context, resource, objects);
		resourceId=resource;
	}
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view=LayoutInflater.from(getContext()).inflate(resourceId, null);
		Drawable[] drawables=getItem(position);
		ImageButton1[] button=new ImageButton1[9];
		int start=R.id.bu0;
		for(int i=0;i<8;++i)
		{	
			button[i]=(ImageButton1)view.findViewById(R.id.bu0+i);
			button[i].setBackground(drawables[i+1]);
			button[i].setFocusable(false);
		}
		button[8]=(ImageButton1) view.findViewById(R.id.bu8);
		button[8].setBackground(drawables[0]);
		button[8].setFocusable(false);
	
		
		return view;
	}
	
	
}
