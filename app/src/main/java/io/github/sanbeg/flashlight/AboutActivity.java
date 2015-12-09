package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity  {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.i_love_it:
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=io.github.sanbeg.flashlight"));
			startActivity(intent);
			break;
		case R.id.needs_work:
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sanbeg/flashlight/issues/"));
			startActivity(intent);
			break;
		case R.id.maybe_later:
			//Go ahead and continue with your application
			finish();
			break;
		}
	}
}