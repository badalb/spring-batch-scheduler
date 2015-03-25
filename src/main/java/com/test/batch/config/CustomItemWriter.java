package com.test.batch.config;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CustomItemWriter implements ItemWriter<Object> {

	
	public void write(List<?> object) throws Exception {
		System.out.println(object.get(0));
	}
}