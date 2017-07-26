package com.cookweather.app.util;

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
}
