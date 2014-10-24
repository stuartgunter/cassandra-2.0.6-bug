 cassandra-2.0.6-bug
===================

This is a very simple project to demonstrate some potentially breaking changes in Cassandra 2.0.6.

The functionality is around removing the final item from a set, which has changed between v2.0.5 and v2.0.6.

In v2.0.5, when removing the last item from a set, the row remained in Cassandra (just the id).
In v2.0.6, when removing the last item from a set, the row is REMOVED from Cassandra (even though the query only instructed to remove the item from the set, not the whole row).

To demonstrate this, run the following two commands:

`mvn clean verify -P cassandra-2.0.5`

and

`mvn clean verify -P cassandra-2.0.6`
