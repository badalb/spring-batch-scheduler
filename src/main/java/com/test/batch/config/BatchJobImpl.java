package com.test.batch.config;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatchJobImpl implements BatchJob {

	@Autowired
	@Qualifier("jobLauncher")
	private JobLauncher jobLauncher;

	@Autowired
	private JobOperator jobOperator;

	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	@Qualifier("jobRepository")
	private JobRepository jobRepository;

	@Autowired
	@Qualifier("step")
	private Step step;

	@Override
	@Scheduled(cron = "0 0/1 * * * ?")
	public void runBatchJob() {

		Job myJob = jobBuilders.get("resam-batch-job").start(step).build();
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("date", new Date());
		builder.addString("source", "resam-batch-job");

		try {

			jobLauncher.run(myJob, builder.toJobParameters());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
