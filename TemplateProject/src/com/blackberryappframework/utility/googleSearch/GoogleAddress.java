package com.blackberryappframework.utility.googleSearch;

public class GoogleAddress {
	
	private String streetNumber;
	public void setStreetNumber(String number) {this.streetNumber = number;}
	public String getStreetNumber() { return this.streetNumber; }
	
	private String street;
	public void setStreet(String street) { this.street = street; }
	public String getStreet() { return this.street;}
	
	private String city;
	public void setCity(String city) { this.city = city; }
	public String getCity() { return this.city;}
	
	private String province;
	public void setProvince(String province) { this.province = province; }
	public String getProvince() { return this.province;}
	
	private String zipcode;
	public void setZipcode(String zipcode) { this.zipcode = zipcode; }
	public String getZipcode() { return this.zipcode;}

	private String country;
	public void setCountry(String country) { this.country = country; }
	public String getCountry() { return this.country;}
	
	private float latitude;
	public void setLatitude(float latitude) { this.latitude = latitude; }
	public float getLatitude() { return this.latitude;}
	
	private float longtitude;
	public void setLongtitude(float longtitude) { this.longtitude = longtitude; }
	public float getLongtitude() { return this.longtitude;}
	
	public String firstLine() {
		StringBuffer formatedAddress =  new StringBuffer();
		
		if (this.streetNumber!= null && this.streetNumber.trim().length() != 0) {
			formatedAddress.append(this.streetNumber + " ");
		}
		if (this.street!= null && this.street.trim().length() != 0) {
			formatedAddress.append(this.street);
		}

		return formatedAddress.toString();
	}
	public String secondLine() {
		StringBuffer formatedAddress =  new StringBuffer();
		
		if (this.city!= null && this.city.trim().length() != 0) {
			formatedAddress.append(this.city + ", ");
		}
		if (this.province!= null && this.province.trim().length() != 0) {
			formatedAddress.append(this.province);
		}
		if (this.zipcode!= null && this.zipcode.trim().length() != 0) {
			formatedAddress.append(" " + this.zipcode);
		}

		return formatedAddress.toString();
	}
	
	public String thirdLine() {
		if (this.country!= null && this.country.trim().length() != 0) {
			return this.country;
		} else
			return "";
	}
	
	public String toString() {
		StringBuffer formatedAddress =  new StringBuffer();
		
		String add = this.firstLine();
		if (add!= null && add.trim().length() != 0) {
			formatedAddress.append(add + ", ");
		}
		
		add = this.secondLine();
		if (add!= null && add.trim().length() != 0) {
			formatedAddress.append(add);// + ", ");
		}
/*
		add = this.thirdLine();
		if (add!= null && add.trim().length() != 0) {
			formatedAddress.append(add);
		}
*/
		return formatedAddress.toString();
	}
}
