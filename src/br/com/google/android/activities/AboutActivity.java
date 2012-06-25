package br.com.google.android.activities;

import roboguice.inject.ContentView;
import android.os.Bundle;
import br.com.google.android.R;

@ContentView(R.layout.sobre)
public class AboutActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}