package spring.batch.demon;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

/**
 * coffeeListener
 * Spring Cloud Stream 을 활용하여, RabbitMQ 메시지 큐 소비자 구현
 *
 * @author Eddy.Kim
 */

@EnableBinding(BrunchListener.Sink.class)
public class BrunchListener {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job brunchProcessJob;

    @StreamListener(Sink.inboundTest)
    public void subscribe() {

        try{
            jobLauncher.run(brunchProcessJob, new JobParametersBuilder()
                    .addLong("timestamp",
                            System.currentTimeMillis())
                    .toJobParameters());
        }
        catch(Exception e){
        }
    }

    public interface Sink {
        String inboundTest = "SINK-BRUNCH";

        @Input(inboundTest)
        SubscribableChannel BrunchListener();
    }
}
