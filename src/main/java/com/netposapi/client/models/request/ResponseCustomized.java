package com.netposapi.client.models.request;

import java.util.HashMap;

public class ResponseCustomized {
    	public static <T> Object response(String message,T object) {
		return new HashMap<String, T>() {			
			private static final long serialVersionUID = 1L;
		{
		   put(message,(T) object);
		}};
}
}