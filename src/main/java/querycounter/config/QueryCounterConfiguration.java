package querycounter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import querycounter.counter.HibernateQueryCounter;
import querycounter.counter.QueryCounterProperties;

@EnableConfigurationProperties(QueryCounterProperties.class)
@Configuration
public class QueryCounterConfiguration {

    public final QueryCounterProperties queryCounterProperties;

    public QueryCounterConfiguration(QueryCounterProperties queryCounterProperties) {
        this.queryCounterProperties = queryCounterProperties;
    }

    @ConditionalOnProperty("query-counter.enabled")
    @Bean
    public HibernateQueryCounter queryCounter() {
        return new HibernateQueryCounter(queryCounterProperties);
    }

    @ConditionalOnProperty("query-counter.enabled")
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties ->
            hibernateProperties.put("hibernate.session_factory.interceptor", queryCounter());
    }
}
