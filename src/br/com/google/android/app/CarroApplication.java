package br.com.google.android.app;

import android.app.Application;
import br.com.google.android.utils.DownloadImagemUtil;

public class CarroApplication extends Application {
	
	private static DownloadImagemUtil downloadImagemUtil = null;
	private static CarroApplication instance = null;
	
	public static CarroApplication getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Configure a aplicação no AndroidManifest.xml");
		}
		
		return instance;
	}
	
	public void onCreate() {
		downloadImagemUtil = new DownloadImagemUtil(this);
		instance = this;
	};
	
	public static DownloadImagemUtil getDownloader() {
		return downloadImagemUtil;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		
		downloadImagemUtil = null;
	}
}
