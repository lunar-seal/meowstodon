package org.joinmastodon.android.ui.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import org.joinmastodon.android.GlobalUserPreferences;

// Rainbow color coding for account handles
// for the purposes of this class, a full account handle is of the form @user@instance
public class HandleColors{

	// Convenience overload to accept user@instance schema
	public static CharSequence colorizeHandle(String acct){
		int at=acct.indexOf('@');
		return colorizeHandle(at>=0 ? acct.substring(0, at) : acct, at>=0 ? acct.substring(at+1) : null);
	}

	public static CharSequence colorizeHandle(String user, String instance){
		// make username color depend on the full handle, as usernames are only unique per instance
		SpannableStringBuilder ssb=new SpannableStringBuilder();
		ssb.append('@');
		int userStart=ssb.length();
		ssb.append(user);
		if(GlobalUserPreferences.colorUsernames)
			ssb.setSpan(new ForegroundColorSpan(stringToColor(user+instance)), userStart, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (instance != null){
			ssb.append('@');
			int domainStart=ssb.length();
			ssb.append(instance);
			if(GlobalUserPreferences.colorDomains)
				ssb.setSpan(new ForegroundColorSpan(stringToColor(instance)), domainStart, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return ssb;
	}

	private static int stringToColor(String string){
		// Fibonacci hashing; multiply by golden-ratio
		int hue=Math.floorMod((string.hashCode()*0x9E3779B1)>>>23, 360);
		// Make colors legible against light/dark theme
		float[] hsv=UiUtils.isDarkTheme() ? new float[]{hue, 0.45f, 0.95f} : new float[]{hue, 0.72f, 0.60f};
		return Color.HSVToColor(hsv);
	}
}
