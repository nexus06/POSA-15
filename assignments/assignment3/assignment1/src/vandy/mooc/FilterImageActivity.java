package vandy.mooc;

import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class FilterImageActivity extends GenericImageActivity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
    final public static String FILTER_IMAGE_ACTION = "action.FILTER_IMAGE";
    
    



	@Override
	protected Uri doInBackgroundHook(Context context, Uri uri) {
		Log.d(TAG, "filtering "+uri.toString());
		//filter image in background
		return Utils.grayScaleFilter(context, uri);

	}
	
}
