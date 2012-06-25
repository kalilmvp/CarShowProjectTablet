package br.com.google.android.activities;

import roboguice.inject.ContentView;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import br.com.google.android.R;
import br.com.google.android.fragments.FragmentListCar;
import br.com.google.android.fragments.FragmentListCarTablet;
import br.com.google.android.pojos.Car;
import br.com.google.android.utils.AdMobsUtils;
import br.com.google.android.utils.AnalyticsUtils;

@ContentView(R.layout.cars)
public class CarListTabletActivity extends BaseActivity {
	
	//flag para não atualizar o FragmentTransactiona ao girar a tela.
	private boolean atualizarTabs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AnalyticsUtils.getInstance(this).trackPageView("/lista_carros_3.0");
		
		final ActionBar actionBar = this.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		final String tipoCarro = this.getIntent().getStringExtra(Car.TYPE);
		
		//Carro - Classico
		final FragmentListCar fragmentListCarClassico = new FragmentListCarTablet();
		Bundle args = new Bundle();
		args.putString(Car.TYPE, Car.Type.TYPE_CLASSIC.getType());
		fragmentListCarClassico.setArguments(args);
		
		//Carro - Luxo
		final FragmentListCar fragmentListCarLuxo = new FragmentListCarTablet();
		args = new Bundle();
		args.putString(Car.TYPE, Car.Type.TYPE_LUXURY.getType());
		fragmentListCarLuxo.setArguments(args);
				
		//Carro - Esportivo
		final FragmentListCar fragmentListCarEsportivo = new FragmentListCarTablet();
		args = new Bundle();
		args.putString(Car.TYPE, Car.Type.TYPE_SPORT.getType());
		fragmentListCarEsportivo.setArguments(args);
		
		Tab tab1 = actionBar.newTab();
		tab1.setText(R.string.menu_classicos);
		tab1.setTabListener(new TabSelecaoCarros(fragmentListCarClassico));
		
		Tab tab2 = actionBar.newTab();
		tab2.setText(R.string.menu_luxo);
		tab2.setTabListener(new TabSelecaoCarros(fragmentListCarLuxo));
		
		Tab tab3 = actionBar.newTab();
		tab3.setText(R.string.menu_esportivos);
		tab3.setTabListener(new TabSelecaoCarros(fragmentListCarEsportivo));
		
		this.atualizarTabs = true;
		
		actionBar.addTab(tab1, savedInstanceState == null ? 
				Car.Type.TYPE_CLASSIC.getType().equalsIgnoreCase(tipoCarro) : false);
		actionBar.addTab(tab2, savedInstanceState == null ? 
				Car.Type.TYPE_LUXURY.getType().equalsIgnoreCase(tipoCarro) : false);
		actionBar.addTab(tab3, savedInstanceState == null ? 
				Car.Type.TYPE_SPORT.getType().equalsIgnoreCase(tipoCarro) : false);
		
		if (savedInstanceState != null) {
			final int tabIndice = savedInstanceState.getInt("tabIndice");
			this.atualizarTabs = false;
			actionBar.setSelectedNavigationItem(tabIndice);
		}
		
		AdMobsUtils.addAdView(this);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		int tabIndice = this.getActionBar().getSelectedNavigationIndex();
		outState.putInt("tabIndice", tabIndice);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item != null) {
			switch (item.getItemId()) {
			
				case android.R.id.home:
					Intent intent = new Intent(this, HomeActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
					this.startActivity(intent);
					
					break;	
				default:
					break;
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class TabSelecaoCarros implements TabListener{
		private Fragment fragmentListCar;
		
		TabSelecaoCarros(Fragment fragmentLisCar) {
			this.fragmentListCar = fragmentLisCar;
		}

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			if (!atualizarTabs) {
				this.fragmentListCar = null;
			} else {
				if (this.fragmentListCar != null) {
					FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
					fragmentTransaction.replace(R.id.layout_esquerda, this.fragmentListCar);
					fragmentTransaction.commit();
				}
			}
			atualizarTabs = true;
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			if (this.fragmentListCar != null) {
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.remove(this.fragmentListCar);
				fragmentTransaction.commit();
			}
		}
	}
}