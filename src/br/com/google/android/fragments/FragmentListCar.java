package br.com.google.android.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.google.android.R;
import br.com.google.android.activities.CarDetailActivity;
import br.com.google.android.adapters.CarAdapter;
import br.com.google.android.pojos.Car;
import br.com.google.android.pojos.ListCars;
import br.com.google.android.services.CarService;
import br.com.google.android.utils.Transacao;

public class FragmentListCar extends BaseFragment implements OnItemClickListener, Transacao {
	
	protected ListView list;
	protected List<Car> cars;
	private String tipo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_carros, null);
		Bundle args = this.getArguments();
		if (args != null) {
			this.tipo = args.getString(Car.TYPE);
		}
		
		if (this.tipo == null) {
			this.tipo = Car.Type.TYPE_LUXURY.getType();
		}
		
		this.list = (ListView) view.findViewById(R.id.listCars);
		this.list.setOnItemClickListener(this);
	
		this.setProgressId(R.id.progressBar);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (savedInstanceState != null) {
			
			ListCars lista = (ListCars) savedInstanceState.getSerializable(ListCars.KEY);
			
			Log.i(FragmentListCar.class.getSimpleName(), "Lendo estado: savedInstanceState(carros)");
			
			this.cars = lista.getCars();
		}
		
		if (this.cars != null) {
			this.atualizarView();
		} else {
			this.startTransacao(this);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		Log.i(FragmentListCar.class.getSimpleName(), "Salvando estado: savedInstanceState(carros)");
		
		outState.putSerializable(ListCars.KEY, new ListCars(this.cars));
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
		Car c = (Car) adapterView.getAdapter().getItem(posicao);
		visualizarDetalhes(c, true);
	}

	private void visualizarDetalhes(Car c, boolean exibeDetalhes) {
		FragmentDetailCar fragmentDetailCar = new FragmentDetailCar();
		Bundle bundle = new Bundle();
		bundle.putSerializable(Car.KEY, c);
		
		fragmentDetailCar.setArguments(bundle);
		
		FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
		View layoutDireita = this.getActivity().findViewById(R.id.layout_direita);
		if (layoutDireita != null) {
			fragmentTransaction.replace(R.id.layout_direita, fragmentDetailCar);
			fragmentTransaction.commit();
		} else {
			if (exibeDetalhes) {
				Intent intent = new Intent(this.getActivity(), CarDetailActivity.class);
				intent.putExtra(Car.KEY, c);
				this.startActivity(intent);
			}
		}
	}

	@Override
	public void executar() throws Exception {
		this.cars = CarService.getCars(this.getActivity(), tipo);
	}

	@Override
	public void atualizarView() {
		if (this.cars != null && !this.cars.isEmpty() && this.getActivity() != null) {
			this.list.setAdapter(new CarAdapter(this.getActivity(), this.cars));
		}
	};
}