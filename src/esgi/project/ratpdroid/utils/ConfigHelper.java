package esgi.project.ratpdroid.utils;

import java.util.Locale;

import esgi.project.ratpdroid.R;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MenuItem;
import android.widget.Toast;

public class ConfigHelper {
	
	private ConfigHelper()
	{}
 
	private static ConfigHelper instance = new ConfigHelper();
 
	public static ConfigHelper getInstance()
	{	
		return instance;
	}
	
	public void changeLang(Context context,String lang)
	{
	    if (lang.equalsIgnoreCase(""))
	    	return;
	    
	    Locale locale = new Locale(lang);
	    Locale.setDefault(locale);
	    Configuration config = new android.content.res.Configuration();
	    config.locale = locale;
	    context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}
}
