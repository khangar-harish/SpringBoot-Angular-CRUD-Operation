package com.springboot.crud.MapperUtil;

import com.springboot.crud.model.Address;
import com.springboot.crud.model.Student;

public class MapperTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * List<String> list = new ArrayList<String>(); list.add("English");
		 * list.add("Hindi"); list.add("Marathi");
		 */
		String list[] = {"English","Hindi","Marathi"};
		Address addr = new Address();
		addr.setStreet("Baker");
		addr.setCity("Vegas");
		addr.setZip("12345");
		addr.setCountry("USA");
		Student st = new Student();
		st.setId(1);
		st.setFirstName("Hash");
		st.setLastName("Khan");
		st.setEmailId("hash@gmail.com");
		st.setAddress(addr);
		st.setLanguages(list);
		
		String stJson = JacksonMapper.javaTojson(st);
		System.out.println("Json String: "+stJson);
		Student stObj = JacksonMapper.jsonTojava(stJson, Student.class);
		System.out.println("Student Object: "+stObj.getId()+" "+stObj.getFirstName()+" "+stObj.getLastName()+" "+stObj.getEmailId());
		System.out.println("Address Object: "+stObj.getAddress().getStreet()+" "+stObj.getAddress().getCity()+" "+stObj.getAddress().getZip()+" "+stObj.getAddress().getCountry());
		System.out.print("Languages: ");
		for (String language : stObj.getLanguages()) {
			System.out.print(language+" ");
		}
	}
}
