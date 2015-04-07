package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
    	super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
    	final Uri uri = getIntent().getData();

        
    	
    	
    	
    	 // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.
    	
    	//handler attached to the UI thread
    	final Handler uiHandler = new Handler(Looper.getMainLooper());
    	
    	// Download the image in the background, and set this as the
        // result of the Activity.
    	Runnable downLoadRunnable = new Runnable() {
			
			@Override
			public void run() {
				
				//create an Intent that contains the path to the image file
				Intent downLoadIntent = new Intent();
				
				//set uri representing imagePath to imageUri using utils class provided
				//this method return null if catch an error so we don't have to manage error here
				Uri imageUri = DownloadUtils.downloadImage(DownloadImageActivity.this, uri);
				
				//set the location of the image
				downLoadIntent.setData(imageUri);
				
				//set result
				if (imageUri !=null){
					setResult(RESULT_OK, downLoadIntent);	
				}else{
					setResult(RESULT_CANCELED, downLoadIntent);	
				}
				
				
				//this runnable will finish the activity
				Runnable postOnUi = new Runnable() {
					
					@Override
					public void run() {
						finish();
					}
				};
				uiHandler.post(postOnUi);
				//also we can call runOnUiThread(postOnUi) what it is the common way, but as exercise we are using a handler attached to UI
			
			}
		};
		
		Thread downloadThread = new Thread(downLoadRunnable);
		downloadThread.start();
		
       
    }
}
