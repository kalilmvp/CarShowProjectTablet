package br.com.google.android.activities;

import roboguice.inject.ContentView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import br.com.google.android.R;
import br.com.google.android.fragments.FragmentDetailCar;

@ContentView(R.layout.car_detail)
public class CarDetailActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			FragmentDetailCar fragmentDetailCar = new FragmentDetailCar();
			
			fragmentDetailCar.setArguments(this.getIntent().getExtras());
			
			FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.layout, fragmentDetailCar);
			fragmentTransaction.commit();
		}
	}
}