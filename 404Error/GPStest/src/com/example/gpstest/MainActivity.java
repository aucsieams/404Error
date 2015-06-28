package com.example.gpstest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity{

	private LocationManager mLocationManager;               //宣告定位管理控制
	private ArrayList<Poi> Pois = new ArrayList<Poi>();   //建立List，屬性為Poi物件
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Pois.add(new Poi("大潤發" , 25.04661 , 121.5168 ));
        Pois.add(new Poi("家樂福" , 24.13682 , 120.6850 ));
        Pois.add(new Poi("愛買" , 25.03362 , 121.56500 ));
        Pois.add(new Poi("COSTCO" , 22.61177 , 120.30031 ));
        Pois.add(new Poi("比漾" , 25.10988 , 121.84519 ));
         
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new OnClickListener(){
          public void onClick(View v){
              mLocationManager.requestLocationUpdates
              (LocationManager.NETWORK_PROVIDER,0,10000.0f,LocationChange);
          }
        });
 }

 public LocationListener LocationChange = new LocationListener(){
      public void onLocationChanged(Location mLocation){
       for(Poi mPoi : Pois){
         mPoi.setDistance(Distance(mLocation.getLatitude(),
                                   mLocation.getLongitude(),
                                   mPoi.getLatitude(),
                                   mPoi.getLongitude()));
         }
            
         DistanceSort(Pois);

       Log.d("TAG", "我的座標 - 經度 : " + mLocation.getLongitude() + "  , 緯度 : " + mLocation.getLatitude() );

        for(int i = 0 ; i < Pois.size() ; i++ ){
           Log.d("TAG", "地點 : " + Pois.get(i).getName() + "  , 距離為 : " + DistanceText(Pois.get(i).getDistance()) ); 
         }
     }

     public void onProviderDisabled(String provider){
     }
          
     public void onProviderEnabled(String provider){
     }
          
     public void onStatusChanged(String provider, int status,Bundle extras){
     }
 };

 @Override
 protected void onDestroy(){
    super.onDestroy();
    mLocationManager.removeUpdates(LocationChange);  //程式結束時停止定位更新
 }

 private String DistanceText(double distance){
    if(distance < 1000 ) return String.valueOf((int)distance) + "m" ;
    else return new DecimalFormat("#.00").format(distance/1000) + "km" ;
 }

 private void DistanceSort(ArrayList<Poi> poi){
    Collections.sort(poi, new Comparator<Poi>(){
       @Override
       public int compare(Poi poi1, Poi poi2){
           return poi1.getDistance() < poi2.getDistance() ? -1 : 1 ;
       }
    });
 }

 public double Distance(double longitude1, double latitude1, double longitude2,double latitude2){
    double radLatitude1 = latitude1 * Math.PI / 180;
    double radLatitude2 = latitude2 * Math.PI / 180;
    double l = radLatitude1 - radLatitude2;
    double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
    double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                     + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                     * Math.pow(Math.sin(p / 2), 2)));
    distance = distance * 6378137.0;
    distance = Math.round(distance * 10000) / 10000;

    return distance ;
 }

    

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
