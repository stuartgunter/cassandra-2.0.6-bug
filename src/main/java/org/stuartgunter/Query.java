package org.stuartgunter;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import static com.datastax.driver.core.querybuilder.QueryBuilder.add;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.remove;

public class Query {

    private final Session session;

    public Query(Session session) {
        this.session = session;
    }

    public void addToSet(String id, String value) {
        session.execute(QueryBuilder.update("test_set_table")
                .with(add("set_col", value))
                .where(eq("id_col", id)));
    }

    public void removeFromSet(String id, String value) {
        session.execute(QueryBuilder.update("test_set_table")
                .with(remove("set_col", value))
                .where(eq("id_col", id)));
    }

    public void addToList(String id, String value) {
        session.execute(QueryBuilder.update("test_list_table")
                .with(add("list_col", value))
                .where(eq("id_col", id)));
    }

    public void removeFromList(String id, String value) {
        session.execute(QueryBuilder.update("test_list_table")
                .with(remove("list_col", value))
                .where(eq("id_col", id)));
    }
}
