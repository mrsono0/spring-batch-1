package spring.batch.demon;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableBatchProcessing
public class DemonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemonApplication.class, args);
	}


	@Bean(name = "threadPoolTaskExecutorByCoffee")
	public ThreadPoolTaskExecutor taskExecutorByCoffee() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(6);
		pool.setMaxPoolSize(30);
		pool.setWaitForTasksToCompleteOnShutdown(false);
		return pool;
	}

	@Bean(name = "threadPoolTaskExecutorByBrunch")
	public ThreadPoolTaskExecutor taskExecutorByBrunch() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(2);
		pool.setMaxPoolSize(2);
		pool.setWaitForTasksToCompleteOnShutdown(false);
		return pool;
	}
}
