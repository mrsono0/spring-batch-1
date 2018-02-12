package spring.batch.demon;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * rbiListener
 * Spring Cloud Stream 을 활용하여, RabbitMQ 메시지 큐 소비자 구현
 * Thread Safe 인지 확인 필요
 *
 * @author Eddy.Kim
 */

@EnableAsync
@EnableBinding(rbiListener.Sink.class)
public class rbiListener {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processJob;

    @Async("threadPoolTaskExecutor")
    @StreamListener(Sink.inboundTest)
    public void subscribe(Order order) {

        try{
            jobLauncher.run(processJob, new JobParametersBuilder()
                    .addLong("timestamp",
                            System.currentTimeMillis())
                    .addString("coffeePath", order.getCoffeePath())
                    .toJobParameters());
        }
        catch(Exception e){
        }
    }

    public interface Sink {
        String inboundTest = "SINK-TEST";

        @Input(inboundTest)
        SubscribableChannel rbiListener();
    }
}
