package org.stuartgunter;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.junit.Test;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryTest {

    private final Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
    private final Session session = cluster.connect("test_ks");

    @Test
    public void name() throws Exception {
        // this is interesting... it seems you CANNOT insert an empty set in 2.0.6 which was possible in 2.0.5
        session.execute("INSERT INTO test_set_table (id_col, set_col) VALUES ('some-identifier', {});");

        assertThatResultSetHasSize(1, "test_set_table");
    }

    @Test
    public void addsAndRemovesFromSet() throws Exception {
        final Query query = new Query(session);

        query.addToSet("test-pk", "test-value");
        assertThatResultSetHasSize(1, "test_set_table");

        query.removeFromSet("test-pk", "test-value");
        assertThatResultSetHasSize(1, "test_set_table"); // we've removed the value from the set, not the row in its entirety
    }

    @Test
    public void addsAndRemovesFromList() throws Exception {
        final Query query = new Query(session);

        query.addToList("test-pk", "test-value");
        assertThatResultSetHasSize(1, "test_list_table");

        query.removeFromList("test-pk", "test-value");
        assertThatResultSetHasSize(1, "test_list_table"); // we've removed the value from the list, not the row in its entirety
    }

    private void assertThatResultSetHasSize(int expectedSize, String tableName) {
        final ResultSet resultAfterAdding = session.execute(QueryBuilder.select().from(tableName).where(eq("id_col", "test-pk")));
        assertThat(resultAfterAdding.getAvailableWithoutFetching()).isEqualTo(expectedSize);
    }
}
