package querycounter.counter;

import java.util.Objects;

public enum QueryType {
    READ, WRITE;

    public static QueryType find(String sql) {
        Objects.requireNonNull(sql, "SQL should not be null.");
        String adjustedSql = sql.toLowerCase();
        if (isQuery(adjustedSql)) {
            return READ;
        }
        if (isCommand(adjustedSql)) {
            return WRITE;
        }
        throw new IllegalArgumentException("SQL is not one of DML.");
    }

    private static boolean isQuery(String sql) {
        return sql.startsWith("select");
    }

    private static boolean isCommand(String sql) {
        return sql.startsWith("insert")
            || sql.startsWith("update")
            || sql.startsWith("delete");
    }
}
