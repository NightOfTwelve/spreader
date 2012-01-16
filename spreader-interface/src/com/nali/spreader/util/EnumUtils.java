package com.nali.spreader.util;

import com.nali.lang.StringUtils;

public class EnumUtils {
  private EnumUtils() {
  }
  
  public static boolean matchEnum(String match, int value, String name, String...descriptions) {
	  if(StringUtils.isEmptyNoOffset(match)) {
		  throw new IllegalArgumentException("Match string is null or empty!");
	  }
	  if(!String.valueOf(value).equals(match)) {
		  if(!name.equalsIgnoreCase(match)) {
			  for(String description : descriptions) {
				  if(description.equals(match)) {
					  break;
				  }
			  }
			  return false;
		  }
	  }
	  return true;
  }
}
