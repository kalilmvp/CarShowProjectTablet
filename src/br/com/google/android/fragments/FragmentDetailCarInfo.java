package br.com.google.android.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import br.com.google.android.R;
import br.com.google.android.activities.AboutActivity;
import br.com.google.android.pojos.Car;

public class FragmentDetailCarInfo extends BaseFragment {
	private WebView webView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_sobre, null);
		
		this.webView = (WebView) view.findViewById(R.id.webview);
		
		WebSettings settings = this.webView.getSettings();
		settings.setJavaScriptEnabled(true);
	
		this.monitaCarregamentoWebView();
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		return view;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		final Bundle args = this.getArguments();
		Car car = null;
		if (args != null) {
			car = (Car) args.getSerializable(Car.KEY);
			this.webView.loadUrl(car.getUrlInfo());
		}
	}
	
	private void monitaCarregamentoWebView() {
		this.webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				Log.i(FragmentAbout.class.getSimpleName(), "Iniciou");
				
				final ProgressBar progress = (ProgressBar) getView().findViewById(R.id.progressBar);
				progress.setVisibility(View.VISIBLE);
			};
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Log.i(AboutActivity.class.getSimpleName(), "Concluiu");
				
				final ProgressBar progress = (ProgressBar) getView().findViewById(R.id.progressBar);
				progress.setVisibility(View.INVISIBLE);
			}
		});
	}
}