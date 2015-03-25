package com.test.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	@Qualifier("resamDataSource")
	private DataSource dataSource;

	@Autowired
	@Qualifier("resamTransactionManager")
	private PlatformTransactionManager transactionManager;

	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	private CustomItemProcessor customItemProcessor;

	@Autowired
	private CustomItemReader customItemReader;

	@Autowired
	private CustomItemWriter customItemWriter;

	@Bean
	@Qualifier("jobRepository")
	public JobRepository createJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();

		factory.setTransactionManager(transactionManager);
		factory.setDataSource(dataSource);
		factory.setDatabaseType("mysql");
		factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
		return (JobRepository) factory.getObject();
	}

	@Bean
	@Qualifier("jobLauncher")
	public JobLauncher jobLauncher() {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		try {
			jobLauncher.setJobRepository(createJobRepository());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jobLauncher;
	}

	@Bean
	@Qualifier("step")
	public Step dataExtractionStep() {
		StepBuilder builder = stepBuilders.get("step");
		return builder.<Object, Object> chunk(1).reader(customItemReader)
				.processor(customItemProcessor).writer(customItemWriter)
				.transactionManager(transactionManager).build();
	}
}
