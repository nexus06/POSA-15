package vandy.mooc;

import java.util.HashMap;
import java.util.Map;

import vandy.mooc.GenericImageActivity.GenericAsyncTask;
import vandy.mooc.MainActivity.ResultCommand;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


public class RetainedFragmentManager extends Fragment {
	 
	 private static final String TAG = RetainedFragmentManager.class.getName();

	private Map<String, Object> mResultMap = new HashMap<>();
	 
	 private boolean isFirstTime = true;
	 
	 
	 

	 public RetainedFragmentManager(Activity context, String tag){
		 FragmentManager fm = context.getFragmentManager();
		     fm.findFragmentByTag(tag);

		    // If the Fragment is non-null, then it is currently being
		    // retained across a configuration change.
		    if (fm.findFragmentByTag(tag) == null) {
		    	fm.beginTransaction().add(this, tag).commit();
		    	isFirstTime = true;
		    }else{
		    	Log.d(TAG, "==retained fragment across change state==");
		    	isFirstTime = false;
		    }
     	
     }

	public Object get(String tag) {
		return mResultMap.get(tag);
	}
	
	

	public void put(String tag, Object uri ) {
		mResultMap.put(tag,  uri);
	}

	public boolean firstTimeIn() {
		return isFirstTime;
	}
	  /**
	   * This method will only be called once when the retained
	   * Fragment is first created.
	   */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // Retain this fragment across configuration changes.
	    setRetainInstance(true);

	  }


}
