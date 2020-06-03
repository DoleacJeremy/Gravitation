package com.JD.MoteurPhysique.manager;

import java.util.Hashtable;

import com.JD.MoteurPhysique.fenetre.param.EnumParam;
import com.JD.MoteurPhysique.fenetre.param.ParamVariable;


public class ParamSettingsManager {
	private Hashtable<EnumParam,ParamVariable<?>> paramSettings = new Hashtable<EnumParam,ParamVariable<?>>();
	private static ParamSettingsManager paramSettingsUser;
	
	private ParamSettingsManager() {
		EnumParam.setUpTypes();
	}
	
	public static ParamSettingsManager getParamSettingsUser() {
		if(null == paramSettingsUser)
			paramSettingsUser = new ParamSettingsManager();
		return(paramSettingsUser);
	}
	
	public ParamVariable<?> getParam(EnumParam settingName){
		return(this.paramSettings.get(settingName));
	}
	public void setParam(EnumParam settingName , ParamVariable<?> paramValue) {
		this.paramSettings.put(settingName, paramValue);
	}
}