package com.example.apploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

import android.widget.Toast;

import android.os.Environment;

import android.content.Context;

import dalvik.system.DexClassLoader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {  
	  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
          
        try {
			FileInputStream fin = new FileInputStream("/data/local/tmp/tonyw");
			FileOutputStream fout = new FileOutputStream(new File(getCacheDir(), "dump"));
						
		
			loadProxy_t("/data/local/tmp/proxy.jar", 
	        		   "lyt/uoybe/wvtv/twdim".replace('/', '.'), 
	        		   "a", 
	        		   new Class [] {InputStream.class, OutputStream.class},
	        		   new Object [] {fin, fout});
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.v("proxy", e.getMessage());
			e.printStackTrace();
		}
        
    }  
    
    private void loadProxy_t(String dexPath,  String clsName, String methodName,  Class [] paramsClasses, Object [] params) {
    	Context context = getApplicationContext();

    	String optPath = context.getDir("proxy", 0).getAbsolutePath();
    	
    	DexClassLoader clsLoader = new DexClassLoader(dexPath, optPath, null, context.getClassLoader());
    	try {
    		Class<?> loadClass = clsLoader.loadClass(clsName);
    		Object instance = loadClass.newInstance();
    		
    		//params
    		//Method method = loadClass.getMethod(methodName, paramsClasses);
    		Method method = loadClass.getDeclaredMethod(methodName, paramsClasses);
    		method.setAccessible(true);
    		
    		method.invoke(instance, params);  
    		
		} catch (Exception e) {
			logInfo(e);
		}
	}
    
    private static void logInfo(Exception e) {
    	Log.v("proxy", e.getMessage());
		e.printStackTrace();
	}
  
}  
