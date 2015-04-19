package vandy.mooc;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public abstract class GenericImageActivity extends LifecycleLoggingActivity {
    private static final String URL = "url";

	/**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
    
    /**
     * display progress
     * */
    private ProgressBar mLoadingProgressBar;

    protected RetainedFragmentManager mRetainedFragmentManager = new RetainedFragmentManager(this, "genericActivityTag");
    
    /**constants in fragmentmanager**/
    private static String URI = "uri";
    private static String IMAGEPATH = "imagepath";
    private static String ASYNCTASk = "asynctask";
    
    abstract protected Uri doInBackgroundHook(Context context, Uri uri);
    
    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "*** GenericImageActivity... ***");
        //final Handler mHandler = new Handler();

        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.generic_image_activity);
        
        mLoadingProgressBar = (ProgressBar)findViewById(R.id.progress_loading);
        
        if (mRetainedFragmentManager.firstTimeIn()){
        	//store the URL into RetainFragmentManager
        	mRetainedFragmentManager.put(URL, getIntent().getData());
        	
        }else{
        	//retainedFragment was previously initialized,
        	//wich means that config change occoured,
        	//so obtain its data and figure out next steps
        	Log.d(TAG, "Second time onCreate() " + (Uri)mRetainedFragmentManager.get(URL));
        	
        	Uri pathToImage = (Uri) mRetainedFragmentManager.get(IMAGEPATH);
        	
        	
        	
        }
        
        


    }
    
    @Override
    protected void onStart(){
    	super.onStart();	
    	
    	GenericAsyncTask asyncTask = (GenericAsyncTask)mRetainedFragmentManager.get(ASYNCTASk);
    	
    	if (asyncTask==null){
    		Log.d(TAG, "onStart() creating and executing asyncTask");
    		mRetainedFragmentManager.put(ASYNCTASk, new GenericAsyncTask().execute((Uri)mRetainedFragmentManager.get(URL)));
    	}else{
    		Log.d(TAG, "onStart() NOT executing asyncTask");
    		
    	}
    	
    	
    	
    }
    
    
    public class GenericAsyncTask extends AsyncTask<Uri, Void, Uri> {
        /**
         * Debugging tag used by the Android logger.
         */
        private final String TAG = getClass().getSimpleName();
        
        public GenericAsyncTask(){
        	
        }
        
        @Override
        protected void onPreExecute(){
        	Log.d(TAG, "onPreExecute()");
        	
        	mLoadingProgressBar.setVisibility(View.VISIBLE);
        	
        	
        }

    	@Override
    	protected Uri doInBackground(Uri... params) {

        	Log.d(TAG, "onInBackground()");
        	Uri imgPath = null;
        	try {
        		imgPath = doInBackgroundHook(GenericImageActivity.this, params[0]);        		
        	
    		} catch (Exception e) {
    			Log.e(TAG, Log.getStackTraceString(e));
    		}
    		return imgPath;
    	}
    	
    	@Override
    	protected void onPostExecute(Uri result){
    		mRetainedFragmentManager.put(IMAGEPATH, result);
    		Log.d(TAG, "finish BG work");
    		
    		//TODO check id canceled
    		Intent intentResult = new Intent();
    		intentResult.setData(result);
    		setResult(RESULT_OK, intentResult);
    		GenericImageActivity.this.finish();
    		
    	}

    }

}
