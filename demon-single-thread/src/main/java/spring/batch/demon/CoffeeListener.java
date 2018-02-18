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

@EnableBinding(CoffeeListener.Sink.class)
public class CoffeeListener {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job coffeeProcessJob;

    @StreamListener(Sink.inboundTest)
    public void subscribe(Order order) {

        try{
            jobLauncher.run(coffeeProcessJob, new JobParametersBuilder()
                    .addLong("timestamp",
                            System.currentTimeMillis())
                    .addString("coffeePath", order.getCoffeePath())
                    .toJobParameters());
        }
        catch(Exception e){
        }
    }

    public interface Sink {
        String inboundTest = "SINK-COFFEE";

        @Input(inboundTest)
        SubscribableChannel CoffeeListener();
    }
}
