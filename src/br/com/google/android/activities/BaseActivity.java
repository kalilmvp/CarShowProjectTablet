package br.com.google.android.activities;

import br.com.google.android.utils.OrientacaoUtils;
import br.com.google.android.utils.Utils;
import android.os.Bundle;
import roboguice.activity.RoboFragmentActivity;

public class BaseActivity extends RoboFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Utils.isTabletWith3(this)) {
			OrientacaoUtils.setOrientationHorizontal(this);
		}
	}
}
