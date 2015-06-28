package com.example.gpstest;

public class Poi {
	private String Name;       
	private double Latitude;   
	private double Longitude;  
	private double Distance;  
    	
	public Poi(String name , double latitude , double longitude){
		Name = name ;
		Latitude = latitude ;
		Longitude = longitude ;
	}
	
	public String getName() {
		return Name;
	}

	public double getLatitude(){
		return Latitude;
	}

	public double getLongitude(){
		return Longitude;
	}
	
	public void setDistance(double distance){
		Distance = distance;
	}
	
	public double getDistance(){
		return Distance;
	}
}
