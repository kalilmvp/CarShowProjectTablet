package br.com.google.android.activities;

import roboguice.inject.ContentView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import br.com.google.android.R;
import br.com.google.android.fragments.FragmentListCar;
import br.com.google.android.utils.AdMobsUtils;

@ContentView(R.layout.cars)
public class CarListActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			FragmentListCar fragmentListCar = new FragmentListCar();
			
			fragmentListCar.setArguments(this.getIntent().getExtras());
			
			FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.layout_esquerda, fragmentListCar);
			fragmentTransaction.commit();
		}
		AdMobsUtils.addAdView(this);
	}
}