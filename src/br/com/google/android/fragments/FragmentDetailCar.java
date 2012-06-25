package br.com.google.android.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.google.android.R;
import br.com.google.android.app.CarroApplication;
import br.com.google.android.pojos.Car;
import br.com.google.android.utils.ActionBarUtil;
import br.com.google.android.utils.AnimationUtil;
import br.com.google.android.utils.Utils;

public class FragmentDetailCar extends BaseFragment implements OnClickListener {
	
	private Car car;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_carro_detalhe, null);
		
		final View header = view.findViewById(R.id.layoutHeader);
		
		if (header != null) {
			header.setVisibility(View.GONE);
		}
		
		Button btAbrirSite = (Button)view.findViewById(R.id.btAbrirSite);
		btAbrirSite.setOnClickListener(this);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Bundle args = this.getArguments();
		if (args != null) {
			this.car = (Car) args.getSerializable(Car.KEY);
			this.updateView();
		}
	}
	
	public void updateView() {
		final View view = this.getView();
		Log.i(FragmentDetailCar.class.getSimpleName(), "Exibindo o carro: " + this.car.getName());
		
		final TextView txtHeader = (TextView) view.findViewById(R.id.tHeader);;
		final TextView txtDesc = (TextView) view.findViewById(R.id.txtDescricao);
		
		if (txtHeader != null) {
			txtHeader.setText(this.car.getName());
		} else {
			if (!Utils.isTablet(this.getActivity())) {
				ActionBarUtil.setActionBarTitle(this.getActivity(), this.car.getName());
			}
		}
		txtDesc.setText(this.car.getDescription());
		
		final ImageView imgView = (ImageView) view.findViewById(R.id.imgCarro);
		Bitmap bitmap = CarroApplication.getDownloader().getBitmap(this.car.getUrlPicture());
		if (bitmap != null) {
			imgView.setImageBitmap(bitmap);
			imgView.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (Utils.isTabletWith3(getActivity())) {
						AnimationUtil.animaFotoSlideLeftToRight(imgView);
					} else {
						AnimationUtil.animaFotoFadeIn(imgView);
					}
				}
			}, 0);
		}
	}
	
	@Override
	public void onClick(View v) {
		FragmentDetailCarInfo fragmentDetailCarInfo = new FragmentDetailCarInfo();
			
		Bundle bundle = new Bundle();
		bundle.putSerializable(Car.KEY, this.car);
		
		fragmentDetailCarInfo.setArguments(bundle);
		
		FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
		View layoutDireita = this.getActivity().findViewById(R.id.layout_direita);
		if (layoutDireita != null) {
			fragmentTransaction.replace(R.id.layout_direita, fragmentDetailCarInfo);
			fragmentTransaction.commit();
		} else {
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.car.getUrlInfo())));
		}
	}
}