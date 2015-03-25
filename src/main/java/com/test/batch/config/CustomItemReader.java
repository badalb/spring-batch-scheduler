package com.test.batch.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Component
@StepScope
public class CustomItemReader implements ItemReader<Object>, InitializingBean {

	@Value("#{jobParameters['source']}")
	public String source;

	private List<String> result;

	private int index = 0;

	public Object read() throws Exception, UnexpectedInputException,
			ParseException {

		if (index < result.size()) {

			String item = result.get(index);
			index++;
			System.out.println("Read[ " + index + " ] = " + item);
			return item;
		} else {
			index = 0;
			result = null;
			return null;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		result = new ArrayList<String>();
		result.add("A");
		result.add("B");

	}

}
