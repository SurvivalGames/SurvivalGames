package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.ArrayList;
import java.util.List;

import com.communitysurvivalgames.thesurvivalgames.objects.JSign;

public class SignManager {
	private static SignManager sm = new SignManager();
	public List<JSign> signs = new ArrayList<JSign>();
	public SignManager() {

	}

	public static SignManager getSignManager() {
		return sm;
	}
}
