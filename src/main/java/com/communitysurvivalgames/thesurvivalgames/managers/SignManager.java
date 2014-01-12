package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.objects.JSign;

import java.util.ArrayList;
import java.util.List;

public class SignManager {
    private static final SignManager sm = new SignManager();
    public List<JSign> signs = new ArrayList<JSign>();

    private SignManager() {

    }

    public static SignManager getSignManager() {
        return sm;
    }
}
