package vandy.mooc;

import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
        Log.d(TAG, "*** DownloadActivity... ***");
        //final Handler mHandler = new Handler();

        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
        super.onCreate(savedInstanceState);
        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
        final Uri uri = getIntent().getData();
        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.
        //Create runnable for downloading
        new Thread(
                new Runnable() {
                    public void run() {
                        Log.d(TAG, "*** Downloading image... ***");
                        Intent myIntent = new Intent();

                        final Uri imageUri = DownloadUtils.downloadImage(getApplicationContext(), uri);
                        myIntent.setData(imageUri);

                        setResult(MainActivity.RESULT_OK, myIntent);

                        // @@ TODO -- you fill in here using the Android "HaMeR"
                        // concurrency framework.  Note that the finish() method
                        // should be called in the UI thread, whereas the other
                        // methods should be called in the background thread.

                        //DownloadImageActivity.this.runOnUiThread(
                        runOnUiThread(
                                new Runnable() {
                                    public void run() {
                                        Log.d(TAG, "*** Downloading image is DONE! ***");
                                        finish();
                                    }
                                }
                        );
                    }
                }
        ).start();


    }
}
