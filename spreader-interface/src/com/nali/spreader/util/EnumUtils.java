package com.nali.spreader.util;

public class EnumUtils {
  private EnumUtils() {
  }
  
  public static boolean matchEnum(String match, int value, String name, String...descriptions) {
	  if(!String.valueOf(value).equals(match)) {
		  if(!name.equalsIgnoreCase(match)) {
			  for(String description : descriptions) {
				  if(description.equals(description)) {
					  break;
				  }
			  }
			  return false;
		  }
	  }
	  return true;
  }
}
