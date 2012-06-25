package br.com.google.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.google.android.R;
import br.com.google.android.activities.AboutActivity;
import br.com.google.android.activities.CarListActivity;
import br.com.google.android.activities.CarListTabletActivity;
import br.com.google.android.pojos.Car;
import br.com.google.android.utils.AnalyticsUtils;
import br.com.google.android.utils.ConexaoUtil;
import br.com.google.android.utils.Utils;

public class FragmentDashboard extends BaseFragment implements OnClickListener {
	private Button btEsportivos;
	private Button btClassico;
	private Button btLuxo;
	private Button btSobre;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard, null);
		
		this.inicializaBotoes(view);
		this.inicializaEventos();
		
		return view;
    }


	private void inicializaBotoes(View view) {
		this.btEsportivos = (Button) view.findViewById(R.id.btEsportivos);
		this.btClassico = (Button) view.findViewById(R.id.btClassicos);
		this.btLuxo = (Button) view.findViewById(R.id.btLuxo);
		this.btSobre = (Button) view.findViewById(R.id.btSobre);
	}


	private void inicializaEventos() {
		this.btEsportivos.setOnClickListener(this);
    	this.btClassico.setOnClickListener(this);
    	this.btLuxo.setOnClickListener(this);
    	this.btSobre.setOnClickListener(this);
	}


	public void onClick(View view) {
		final Context context = view.getContext();
		if (context != null) {
			boolean redeOk = ConexaoUtil.isNetworkAvailable(context);
			if (redeOk) {
				boolean isAndroid3 = Utils.isAndroid3();
				Intent intent = new Intent(context, isAndroid3 ? CarListTabletActivity.class : CarListActivity.class);
				
				if (view == this.btEsportivos) {
					AnalyticsUtils.getInstance(this.getActivity()).trackEvent("Dashboard", "Click", "ESPORTIVO", 0);
					intent.putExtra(Car.TYPE, Car.Type.TYPE_SPORT.getType());
					this.startActivity(intent);
				} else if (view == this.btClassico) {
					AnalyticsUtils.getInstance(this.getActivity()).trackEvent("Dashboard", "Click", "CLASSICO", 0);
					intent.putExtra(Car.TYPE, Car.Type.TYPE_CLASSIC.getType());
					this.startActivity(intent);
				} else if (view == this.btLuxo) {
					AnalyticsUtils.getInstance(this.getActivity()).trackEvent("Dashboard", "Click", "LUXO", 0);
					intent.putExtra(Car.TYPE, Car.Type.TYPE_LUXURY.getType());
					this.startActivity(intent);
				} else {
					if (!Utils.isTabletWith3(this.getActivity())) {
						AnalyticsUtils.getInstance(this.getActivity()).trackEvent("Dashboard", "Click", "SOBRE", 0);
						this.startActivity(new Intent(context, AboutActivity.class));
					}
				}
			}
		}
	}
}