package querycounter.counter;

import java.util.Objects;
import org.hibernate.EmptyInterceptor;
import querycounter.counter.QueryCounterProperties.CountLevel;

public class HibernateQueryCounter extends EmptyInterceptor implements QueryCounter {

    private final QueryCounterProperties queryCounterProperties;
    private final ThreadLocal<Long> queryCounts = new ThreadLocal<>();

    public HibernateQueryCounter(QueryCounterProperties queryCounterProperties) {
        this.queryCounterProperties = queryCounterProperties;
    }

    @Override
    public String onPrepareStatement(String sql) {
        Long currentQueryCounts = queryCounts.get();
        if (Objects.nonNull(currentQueryCounts) && queryCounterProperties.isEnabled()) {
            updateQueryCounts(sql, currentQueryCounts);
        }
        return super.onPrepareStatement(sql);
    }

    private void updateQueryCounts(String sql, Long queryCounts) {
        CountLevel countLevel = queryCounterProperties.getCountLevel();
        QueryType queryType = QueryType.find(sql);
        if (countLevel.supports(queryType)) {
            this.queryCounts.set(queryCounts + 1L);
        }
    }

    @Override
    public void startCountingQuery() {
        queryCounts.set(0L);
    }

    @Override
    public Long getQueryCounts() {
        return queryCounts.get();
    }

    @Override
    public void clear() {
        queryCounts.remove();
    }
}
