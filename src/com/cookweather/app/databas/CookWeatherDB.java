package com.cookweather.app.databas;

import java.util.ArrayList;
import java.util.List;

import com.cookweather.app.model.City;
import com.cookweather.app.model.County;
import com.cookweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CookWeatherDB {
public static final String DB_NAME="cook_weather";
public static final int VERSION=1;
private static CookWeatherDB cookWeatherDB;
private SQLiteDatabase databas;

private CookWeatherDB(Context context){
	CookWeatherOpenHelper dbHelper=new CookWeatherOpenHelper(context, DB_NAME, null, VERSION);
	databas= dbHelper.getWritableDatabase();
}
public synchronized static CookWeatherDB getInstance(Context context){
	if(cookWeatherDB==null){
		cookWeatherDB= new CookWeatherDB(context);
	}
	return cookWeatherDB;
	}
public void savaProvince(Province province){
	if(province!=null){
		ContentValues values=new ContentValues();
		values.put("province_name", province.getProvinceName());
		values.put("province_code", province.getProvinceCode());
		databas.insert("Province", null, values);	
		}
}
public List<Province> loadProvinces(){
	List<Province> list=new ArrayList<Province>();
		Cursor cursor=databas.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
	return list;
	
}
public void savaCity(City city){
	if(city!=null){
		ContentValues values=new ContentValues();
		values.put("city_name", city.getCityName());
		values.put("city_code", city.getCityCode());
		values.put("province_id", city.getProvinceId());
		databas.insert("City", null, values);	
		}
}
public List<City> loadCities(int provinceId){
	List<City> list=new ArrayList<City>();
		Cursor cursor=databas.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
	return list;
	
}
public void savaCounty(County county){
	if(county!=null){
		ContentValues values=new ContentValues();
		values.put("county_name", county.getCountyName());
		values.put("county_code", county.getCountyCode());
		values.put("city_id", county.getCityId());
		databas.insert("County", null, values);	
		}
}
public List<County> loadCounties(int cityId){
	List<County> list=new ArrayList<County>();
		Cursor cursor=databas.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
	return list;
	
}
}
