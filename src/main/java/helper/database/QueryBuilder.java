package helper.database;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private List<String> queryList = new ArrayList<>();

    public QueryBuilder select() {
        queryList.add("SELECT ");
        return this;
    }

    public QueryBuilder select(String ...columns) {
        queryList.add("SELECT " + String.join(", ", columns));
        return this;
    }

    public QueryBuilder insert() {
        queryList.add("INSERT INTO ");
        return this;
    }

    public QueryBuilder table(String table) {
        queryList.add(table);
        return this;
    }

    public QueryBuilder values(String ...values) {
        queryList.add("(" + String.join(",", values));
        return this;
    }

    public QueryBuilder all() {
        queryList.add(" * ");
        return this;
    }

    public QueryBuilder from(String table) {
        queryList.add(" FROM " + table);
        return this;
    }

    public QueryBuilder where() {
        queryList.add(" WHERE ");
        return this;
    }

    public QueryBuilder column(String column) {
        queryList.add(column);
        return this;
    }

    public QueryBuilder lt() {
        queryList.add("<");
        return this;
    }

    public QueryBuilder eq() {
        queryList.add("=");
        return this;
    }

    public QueryBuilder gt() {
        queryList.add(">");
        return this;
    }

    public QueryBuilder and() {
        queryList.add(" AND ");
        return this;
    }

    public QueryBuilder orderBy(String column) {
        queryList.add(" ORDER BY " + column);
        return this;
    }

    public String toSql() {
        return String.join("", queryList);
    }
}
