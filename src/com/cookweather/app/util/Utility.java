package com.cookweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.cookweather.app.databas.CookWeatherDB;
import com.cookweather.app.model.City;
import com.cookweather.app.model.County;
import com.cookweather.app.model.Province;

public class Utility {
      public synchronized static boolean handleProvincesResponse(CookWeatherDB cookWeatherDB,String response){
    	  if(!TextUtils.isEmpty(response)){
    		  String[] allProvinces=response.split(",");
    		  if(allProvinces!=null&&allProvinces.length>0){
    			  for(String p:allProvinces){
    				  String[] array=p.split("\\|");
    				  Province province=new Province();
    				  province.setProvinceCode(array[0]);
    				  province.setProvinceName(array[1]);
    				  cookWeatherDB.savaProvince(province);
    			  }
    			  return true;
    		  }
    	  }
		return false;
    	  
      }
      
      public static boolean handleCitiesResponse(CookWeatherDB cookWeatherDB,String response,int provinceId){
    	  if(!TextUtils.isEmpty(response)){
    		  String[] allCities=response.split(",");
    		  if(allCities!=null&&allCities.length>0){
    			  for(String c:allCities){
    				  String[] array=c.split("\\|");
    				  City city=new City();
    				  city.setCityCode(array[0]);
    				  city.setCityName(array[1]);
    				  city.setProvinceId(provinceId);
    				  cookWeatherDB.savaCity(city);
    			  }
    			  return true;
    		  }
    	  }
		return false;
    	  
      }
      
      public static boolean handleCountiesResponse(CookWeatherDB cookWeatherDB,String response,int cityId){
    	  if(!TextUtils.isEmpty(response)){
    		  String[] allCounties=response.split(",");
    		  if(allCounties!=null&&allCounties.length>0){
    			  for(String c1:allCounties){
    				  String[] array=c1.split("\\|");
    				County county=new County();
    				county.setCountyCode(array[0]);
    				county.setCountyName(array[1]);
    				county.setCityId(cityId);
    				  cookWeatherDB.savaCounty(county);
    			  }
    			  return true;
    		  }
    	  }
		return false;
    	  
      }
      
     public static void handleWeatherResponse(Context context,String response){
    	  try {
			JSONObject jsonObject=new JSONObject(response);
			JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
			String cityName =weatherInfo.getString("city");
			String weatherCode=weatherInfo.getString("cityid");
			String temp1=weatherInfo.getString("temp1");
			String temp2=weatherInfo.getString("temp2");
			String weatherDesp=weatherInfo.getString("weather");
			String publishTime=weatherInfo.getString("ptime");
			savaWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }

	private static void savaWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyƒÍM‘¬d»’",Locale.CHINA);
		SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}
}
