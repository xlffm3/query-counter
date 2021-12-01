package querycounter.counter;

public interface QueryCounter {

    void startCountingQuery();

    Long getQueryCounts();

    void clear();
}
