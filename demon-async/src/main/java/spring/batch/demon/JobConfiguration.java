package spring.batch.demon;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JobConfiguration
 * 스프링 배치 Job, Step, Reader, Processor, Writer 설정
 *
 * @author Eddy.Kim
 */

@Configuration
public class JobConfiguration {

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job processJob() throws Exception {
        return this.jobBuilderFactory.get("processJob")
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    protected Step step1() throws Exception {
        CustomItemProcessor processor = new CustomItemProcessor();
        CustomItemWriter writer = new CustomItemWriter();

        return stepBuilderFactory.get("step1")
                .<Coffee, String>chunk(1)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    @StepScope
    public StaxEventItemReader<Coffee> reader(@Value("#{jobParameters['coffeePath']}")String coffePath){

        XStreamMarshaller unmarshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("coffee", Coffee.class);
        unmarshaller.setAliases(aliases);

        StaxEventItemReader<Coffee> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource(coffePath));
        reader.setFragmentRootElementName("coffee");
        reader.setUnmarshaller(unmarshaller);

        return reader;
    }


    public class CustomItemWriter implements ItemWriter<String> {
        @Override
        public void write(List<? extends String> strCoffee) throws Exception {
            System.out.println(strCoffee);
        }
    }

    public class CustomItemProcessor implements ItemProcessor<Coffee, String> {
        @Override
        public String process(final Coffee coffee) throws Exception {
            return coffee.getName().toUpperCase() + "의 가격은 " + coffee.getPrice();
        }
    }
}