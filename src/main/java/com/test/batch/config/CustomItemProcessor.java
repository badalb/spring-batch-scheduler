package com.test.batch.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CustomItemProcessor implements ItemProcessor<Object, Object> {

	@Value("#{jobParameters['source']}")
	public String source;

	public Object process(Object object) throws Exception {

		return object;

	}
}