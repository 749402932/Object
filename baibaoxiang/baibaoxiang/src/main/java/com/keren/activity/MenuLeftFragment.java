package com.keren.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public  class MenuLeftFragment extends Fragment implements  OnClickListener{
	
    private View view;
    private ImageView kemuImage1;
    private ImageView kemuImage2;
    private ImageView kemuImage3;
    private ImageView kemuImage4;
    private ImageView kemuImage5;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view=inflater.inflate(R.layout.layout_menu, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		findView();
		initEvent();
		
		
		
	}
	public interface  FraClickListener
	{
		void fraClick(View v);
	}
	public void  findView()
	{
		kemuImage1=(ImageView)  view.findViewById(R.id.one);
		kemuImage2=(ImageView)  view.findViewById(R.id.two);
	    kemuImage3=(ImageView)  view.findViewById(R.id.three);
	    kemuImage4=(ImageView) view.findViewById(R.id.four);
		kemuImage5=(ImageView)  view.findViewById(R.id.five);
	}
	public void  initEvent()
	{
		kemuImage1.setOnClickListener(this);
		kemuImage2.setOnClickListener(this);
		kemuImage3.setOnClickListener(this);
		kemuImage4.setOnClickListener(this);
		kemuImage5.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View view)
	{
		
			
			if(getActivity()  instanceof FraClickListener )
			{
				((FraClickListener)  getActivity()).fraClick(view);
			}
		
	}

}
