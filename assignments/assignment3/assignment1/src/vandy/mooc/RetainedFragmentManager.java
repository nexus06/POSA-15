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
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class RetainedFragmentManager {
	
	 private GenericAsyncTask mSavedAsincTask;
	 
	 private Map<String, Object> mResultArray = new HashMap<>();
	 
	 private boolean isFirstTime = true;
	 

	 public RetainedFragmentManager(Context context, String tag){
		 
     	
     }

	public Object get(String tag) {
		return mResultArray.get(tag);
	}
	
	

	public void put(String tag, Object uri ) {
		mResultArray.put(tag,  uri);
	}

	public boolean firstTimeIn() {
		if (isFirstTime){
			isFirstTime = false;
			return true;
		}else{
			return isFirstTime;
		}
	}

}
