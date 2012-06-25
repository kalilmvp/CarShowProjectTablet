package br.com.google.android.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.google.android.R;
import br.com.google.android.app.CarroApplication;
import br.com.google.android.pojos.Car;
import br.com.google.android.utils.DownloadImagemUtil;
import br.com.google.android.utils.OrientacaoUtils;

public class CarAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private final List<Car> cars;
	private final Activity context;
	private DownloadImagemUtil downloader;
	
	public CarAdapter(Activity context, List<Car> cars) {
		this.context = context;
		this.cars = cars;
		this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.downloader = CarroApplication.getDownloader();
	}
	
	@Override
	public int getCount() {
		return this.cars != null && this.cars.size() > 0 ? this.cars.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return this.cars != null && this.cars.size() > 0 ? this.cars.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		
		boolean isHorizontal = false;
		if (OrientacaoUtils.isHorizontal(this.context)) {
			isHorizontal = true;
		}
		
		//there isn´t any view for this line yet, so create a new one.
		if (view == null) {
			viewHolder = new ViewHolder();
			
			int layout = R.layout.car_line;
			view = this.inflater.inflate(layout, null);
			view.setTag(viewHolder);
			
			viewHolder.setTxtNome((TextView)view.findViewById(R.id.txtNome));
			if (isHorizontal) {
				viewHolder.setTxtDescricao((TextView)view.findViewById(R.id.txtDescricao));
			}
			viewHolder.setImg((ImageView)view.findViewById(R.id.img));
			viewHolder.setProgressBar((ProgressBar)view.findViewById(R.id.progressBar));
		} 
		//there is already on the cache, so take it
		else {
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.getImg().setImageBitmap(null);
		
		Car car = this.cars.get(position);
		
		//now we can update the values
		viewHolder.getTxtNome().setText(car.getName());
		if (isHorizontal) {
			viewHolder.getTxtDescricao().setText(car.getDescription());
		}
		this.downloader.download(
				this.context, car.getUrlPicture(), viewHolder.getImg(), viewHolder.getProgressBar());
		
		return view;
	}
	
	
	/**
	 * Design patter ViewHolder
	 * 
	 * @author Kalil
	 */
	private static class ViewHolder {
		private TextView txtNome;
		private TextView txtDescricao;
		private ImageView img;
		private ProgressBar progressBar;
		
		public ProgressBar getProgressBar() {
			return progressBar;
		}
		public void setProgressBar(ProgressBar progressBar) {
			this.progressBar = progressBar;
		}
		public ImageView getImg() {
			return img;
		}
		public void setImg(ImageView img) {
			this.img = img;
		}
		public TextView getTxtNome() {
			return txtNome;
		}
		public void setTxtNome(TextView txtNome) {
			this.txtNome = txtNome;
		}
		public TextView getTxtDescricao() {
			return txtDescricao;
		}
		public void setTxtDescricao(TextView txtDescricao) {
			this.txtDescricao = txtDescricao;
		}
	}
}