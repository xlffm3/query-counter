package querycounter.counter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "query-counter")
@Component
public class QueryCounterProperties {

    private boolean enabled = false;
    private CountLevel countLevel = CountLevel.READ_ONLY;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CountLevel getCountLevel() {
        return countLevel;
    }

    public void setCountLevel(CountLevel countLevel) {
        this.countLevel = countLevel;
    }

    public enum CountLevel {
        READ_ONLY, WRITE_ONLY, ALL;

        public boolean supports(QueryType queryType) {
            if (this == ALL) {
                return true;
            }
            if (this == READ_ONLY && queryType == QueryType.WRITE) {
                return false;
            }
            if (this == WRITE_ONLY && queryType == QueryType.READ) {
                return false;
            }
            return true;
        }
    }
}
