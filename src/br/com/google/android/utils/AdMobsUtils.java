package br.com.google.android.utils;

import android.app.Activity;
import android.widget.LinearLayout;
import br.com.google.android.R;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AdMobsUtils {

	private static final String PUBLISHER_ID = "a14fe3823c2dcbd";
	
	public static void addAdView(Activity activity) {
		
		AdSize adSize = null;
		
		if (!Utils.isTablet(activity)) {
			if (OrientacaoUtils.isHorizontal(activity)) {
				adSize = AdSize.IAB_BANNER;
			} else {
				adSize = AdSize.BANNER;
			}
		} else {
			
		}
		
		AdView adView = new AdView(activity, adSize, PUBLISHER_ID);
	
		LinearLayout layout = (LinearLayout) activity.findViewById(R.id.layout_admobs);
		layout.addView(adView);
		
		AdRequest adRequest = new AdRequest();
		adView.loadAd(adRequest);
	}
}
