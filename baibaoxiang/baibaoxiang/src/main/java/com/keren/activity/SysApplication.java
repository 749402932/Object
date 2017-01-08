package com.keren.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class SysApplication extends Application{
	
            private   List<Activity>  mList=new LinkedList<Activity> ();
            private  static SysApplication  instance;
            private SysApplication()
            {
            	
            }
            public synchronized static SysApplication  getInstance()
            {
            	if(instance==null)
            	{
            		instance=new SysApplication();
            	}
            	return instance;
            }
             public void addActivity(Activity activity)
             {
            	 mList.add(activity);
             }
             public void exit()
             {
            	 try
            	 {
            		 for(Activity  activity:mList)
            		 {
            			 if(activity!=null)
            			 {
            				 activity.finish();
            			 }
            		 }
            	 }
            	 catch (Exception e) {
					// TODO: handle exception
            		 e.printStackTrace();
				}
            	 finally
            	 {
            		 System.exit(0);
            	 }
             }
                 

}
