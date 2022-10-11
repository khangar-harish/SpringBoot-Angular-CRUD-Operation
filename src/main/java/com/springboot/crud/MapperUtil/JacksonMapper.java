package com.springboot.crud.MapperUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper {

	private static ObjectMapper mapper;
	static {
		 mapper = new ObjectMapper();
	}
	
	public static String javaTojson(Object object) {
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			System.out.println("There is problem with Json processing "+e.getMessage());
			e.printStackTrace();
		}
		return jsonString;
	}
	
	public static <T> T jsonTojava(String jsonString,  Class<T> cls) {
		T result = null;
		try {
			result = mapper.readValue(jsonString, cls);
		} catch (JsonMappingException e) {
			System.out.println("There is problem with Json mapping "+e.getMessage());
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			System.out.println("There is problem with Json processing "+e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
