package br.com.google.android.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import br.com.google.android.R;
import br.com.google.android.activities.AboutActivity;
import br.com.google.android.adapters.CarAdapter;
import br.com.google.android.pojos.Car;
import br.com.google.android.utils.Utils;

public class FragmentListCarTablet extends FragmentListCar {
	
	private Menu menu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		this.menu = menu;
		inflater.inflate(R.menu.menu_actionbar, menu);
		
		final MenuItem item = menu.findItem(R.id.menu_busca);
		final SearchView sv = new SearchView(this.getActivity());
		
		sv.setOnQueryTextListener(new FiltroListener());
		
		item.setActionView(sv);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_atualizar: 
			this.atualizarViewMenuAtualizar(true);
			this.startTransacao(this);
			break;
		case R.id.menu_sobre:
			this.invocaSobre();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void invocaSobre() {
		View layoutDireita = this.getActivity().findViewById(R.id.layout_direita);
		
		if (layoutDireita != null) {
			FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.layout_direita, new FragmentAbout());
			fragmentTransaction.commit();	
		} else {
			this.startActivity(new Intent(this.getActivity(), AboutActivity.class));
		}
	}

	private void atualizarViewMenuAtualizar(boolean atualizar) {
		if (this.menu != null) {
			final MenuItem item = this.menu.findItem(R.id.menu_atualizar);
			if (item != null ) {
				if (atualizar) {
					item.setActionView(R.layout.actionbar_progress);
				} else {
					item.setActionView(null);
				}
			}
		}
	}
	
	@Override
	public void atualizarView() {
		super.atualizarView();
		atualizarViewMenuAtualizar(false);
	}
	
	private class FiltroListener implements OnQueryTextListener {

		@Override
		public boolean onQueryTextChange(String newText) {
			if ("".equalsIgnoreCase(newText)) {
				atualizarView();
			}
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			if (cars != null) {
				List<Car> novaLista = new ArrayList<Car>();
				
				for (Car car: cars) {
					boolean contem = car.getName().toUpperCase().contains(query.toUpperCase());
					if (contem) {
						novaLista.add(car);
					}
				}
				
				list.setAdapter(new CarAdapter(getActivity(), novaLista));
				Utils.closeVirtualKeyboard(getActivity(), list);
			}
			return false;
		}
	}
}