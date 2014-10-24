CREATE KEYSPACE test_ks WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE test_ks.test_set_table (
    id_col text PRIMARY KEY,
    set_col set<text>
);

CREATE TABLE test_ks.test_list_table (
    id_col text PRIMARY KEY,
    list_col list<text>
);