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
    public void addsAndRemovesFromSet() throws Exception {
        final Query query = new Query(session);

        query.addToSet("test-pk", "test-value");
        assertTableHasSize(1);

        query.removeFromSet("test-pk", "test-value");
        assertTableHasSize(1); // we've removed the value from the set, not the row in its entirety
    }

    private void assertTableHasSize(int expectedSize) {
        final ResultSet resultAfterAdding = session.execute(QueryBuilder.select().from("test_table").where(eq("id_col", "test-pk")));
        assertThat(resultAfterAdding.getAvailableWithoutFetching()).isEqualTo(expectedSize);
    }
}
