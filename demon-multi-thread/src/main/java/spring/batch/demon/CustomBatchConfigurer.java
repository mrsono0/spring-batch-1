package spring.batch.demon;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * CustomBatchConfigurer
 * 비동기 Async 한 Job 실행을 위하여 DefaultBatchConfigurer 를 상속
 * jobLauncher.setTaskExecutor(taskExecutor); 추가
 *
 * @author Eddy.Kim
 */

@Component
@EnableBatchProcessing
public class CustomBatchConfigurer extends DefaultBatchConfigurer {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    protected JobLauncher createJobLauncher() throws Exception {

        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(super.getJobRepository());
        jobLauncher.afterPropertiesSet();
        jobLauncher.setTaskExecutor(taskExecutor);
        return jobLauncher;
    }
}
