package io.github.sanbeg.flashlight;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends Activity implements OnClickListener{
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        findViewById(R.id.i_love_it).setOnClickListener(this);
        findViewById(R.id.needs_work).setOnClickListener(this);
        findViewById(R.id.maybe_later).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.i_love_it:
			//use your app id below!
			i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=io.github.sanbeg.flashlight"));
			startActivity(i);
			break;
		case R.id.needs_work:
			i = new Intent(android.content.Intent.ACTION_SEND);  
			//use your e-mail address below!
            /*
			i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"ob1@cheesejedi.com"});  
			i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Needs Improvement!");  
			i.setType("plain/text");
			*/
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sanbeg/flashlight/issues/"));
			startActivity(i);  
			break;
		case R.id.maybe_later:
			//Go ahead and continue with your application
			finish();
			break;
		}
	}
}