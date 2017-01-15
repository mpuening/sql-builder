package io.github.sqlbuilder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SelectTests {

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidApiUsageNoTableSpecified() {
		new Select().build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidApiUsageNoTableSpecifiedButColumnIs() {
		new Select().column("column1").build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidApiUsageBadTableSpecified() {
		new Select().from().table("").build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidApiUsageBadJoinTableSpecified() {
		new Select().from().table("table").join("", "").build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidApiUsageBadJoinTableColumnSpecified() {
		new Select().from().table("table1").join("table2", "").build();
	}

	@Test
	public void testSimpleQueries() {
		String query = new Select().from().table("table").build();
		assertEquals("select * from table", query);

		query = new Select().from().table("table", "t").build();
		assertEquals("select * from table t", query);

		query = new Select().from().table("table1").and().table("table2").build();
		assertEquals("select * from table1, table2", query);

		query = new Select().column("column").from().table("table").build();
		assertEquals("select column from table", query);

		query = new Select().column("column", "c").from().table("table", "t").build();
		assertEquals("select column as c from table t", query);

		query = new Select().column("column1").column("column2").from().table("table1").and().table("table2").build();
		assertEquals("select column1, column2 from table1, table2", query);

		query = new Select().from().table("table").where().condition("1=1").build();
		assertEquals("select * from table where 1=1", query);

		query = new Select().from().table("table1").and().table("table2").where().condition("1=1").build();
		assertEquals("select * from table1, table2 where 1=1", query);

		// Repeats are filtered out
		query = new Select().from().table("table1").and().table("table2").and().table("table2").where().condition("1=1")
				.build();
		assertEquals("select * from table1, table2 where 1=1", query);

		query = new Select().column("column1").column("column2").from().table("table1").and().table("table2").where()
				.condition("1=1").condition("2=2").build();
		assertEquals("select column1, column2 from table1, table2 where 1=1 and 2=2", query);

		query = new Select().column("column1").column("column2").from().table("table1").and().table("table2").where()
				.condition("1=1").condition("2=2").condition("2=2").build();
		assertEquals("select column1, column2 from table1, table2 where 1=1 and 2=2", query);

		query = new Select().column("column1").column("column2").from().table("table1").and().table("table2").where()
				.condition("1=1").condition("2=?", 2).condition("2=?", 3).build();
		assertEquals("select column1, column2 from table1, table2 where 1=1 and 2=? and 2=?", query);
	}

	@Test
	public void testQueriesWithTableJoins() {
		String query = new Select().from().table("table1").join("table2", "column").build();
		assertEquals("select * from table1 join table2 on table1.column = table2.column", query);

		query = new Select().from().table("table1", "t1").joinWithAlias("table2", "t2", "column").build();
		assertEquals("select * from table1 t1 join table2 t2 on t1.column = t2.column", query);

		query = new Select().from().table("table1").join("table2", "table1column", "table2column").build();
		assertEquals("select * from table1 join table2 on table1.table1column = table2.table2column", query);

		query = new Select().from().table("table1", "t1").joinWithAlias("table2", "t2", "table1column", "table2column")
				.build();
		assertEquals("select * from table1 t1 join table2 t2 on t1.table1column = t2.table2column", query);

		query = new Select().from().table("table1").join("table2", "column").join("table3", "column").build();
		assertEquals(
				"select * from table1 join table2 on table1.column = table2.column join table3 on table1.column = table3.column",
				query);

		// Repeats are filtered out
		query = new Select().from().table("table1").join("table2", "column").and().table("table1")
				.join("table2", "column").build();
		assertEquals("select * from table1 join table2 on table1.column = table2.column", query);
		query = new Select().from().table("table1", "t1").joinWithAlias("table2", "t2", "column").and()
				.table("table1", "t1").joinWithAlias("table2", "t2", "column").build();
		assertEquals("select * from table1 t1 join table2 t2 on t1.column = t2.column", query);
		query = new Select().from().table("table1").table("table1").table("table1").join("table2", "column").and()
				.table("table1").join("table2", "column").build();
		assertEquals("select * from table1 join table2 on table1.column = table2.column", query);
		query = new Select().from().table("table1").join("table2", "column").join("table2", "column").build();
		assertEquals("select * from table1 join table2 on table1.column = table2.column", query);
	}

	@Test
	public void testQueriesWithTableInnerJoins() {
		String query = new Select().from().table("table1").innerJoin("table2", "column").build();
		assertEquals("select * from table1 inner join table2 on table1.column = table2.column", query);

		query = new Select().from().table("table1", "t1").innerJoinWithAlias("table2", "t2", "column").build();
		assertEquals("select * from table1 t1 inner join table2 t2 on t1.column = t2.column", query);

		query = new Select().from().table("table1").innerJoin("table2", "table1column", "table2column").build();
		assertEquals("select * from table1 inner join table2 on table1.table1column = table2.table2column", query);

		query = new Select().from().table("table1", "t1")
				.innerJoinWithAlias("table2", "t2", "table1column", "table2column").build();
		assertEquals("select * from table1 t1 inner join table2 t2 on t1.table1column = t2.table2column", query);

		query = new Select().from().table("table1").innerJoin("table2", "column").innerJoin("table3", "column").build();
		assertEquals(
				"select * from table1 inner join table2 on table1.column = table2.column inner join table3 on table1.column = table3.column",
				query);
	}

	@Test
	public void testQueriesWithTableOuterJoins() {
		String query = new Select().from().table("table1").outerJoin("table2", "column").build();
		assertEquals("select * from table1 outer join table2 on table1.column = table2.column", query);

		query = new Select().from().table("table1", "t1").outerJoinWithAlias("table2", "t2", "column").build();
		assertEquals("select * from table1 t1 outer join table2 t2 on t1.column = t2.column", query);

		query = new Select().from().table("table1").outerJoin("table2", "table1column", "table2column").build();
		assertEquals("select * from table1 outer join table2 on table1.table1column = table2.table2column", query);

		query = new Select().from().table("table1", "t1")
				.outerJoinWithAlias("table2", "t2", "table1column", "table2column").build();
		assertEquals("select * from table1 t1 outer join table2 t2 on t1.table1column = t2.table2column", query);

		query = new Select().from().table("table1").outerJoin("table2", "column").outerJoin("table3", "column").build();
		assertEquals(
				"select * from table1 outer join table2 on table1.column = table2.column outer join table3 on table1.column = table3.column",
				query);
	}

	@Test
	public void testQueriesWithTableRightJoins() {
		String query = new Select().from().table("table1").rightJoin("table2", "column").build();
		assertEquals("select * from table1 right join table2 on table1.column = table2.column", query);

		query = new Select().from().table("table1", "t1").rightJoinWithAlias("table2", "t2", "column").build();
		assertEquals("select * from table1 t1 right join table2 t2 on t1.column = t2.column", query);

		query = new Select().from().table("table1").rightJoin("table2", "table1column", "table2column").build();
		assertEquals("select * from table1 right join table2 on table1.table1column = table2.table2column", query);

		query = new Select().from().table("table1", "t1")
				.rightJoinWithAlias("table2", "t2", "table1column", "table2column").build();
		assertEquals("select * from table1 t1 right join table2 t2 on t1.table1column = t2.table2column", query);

		query = new Select().from().table("table1").rightJoin("table2", "column").rightJoin("table3", "column").build();
		assertEquals(
				"select * from table1 right join table2 on table1.column = table2.column right join table3 on table1.column = table3.column",
				query);
	}

	@Test
	public void testQueriesWithTableLeftJoins() {
		String query = new Select().from().table("table1").leftJoin("table2", "column").build();
		assertEquals("select * from table1 left join table2 on table1.column = table2.column", query);

		query = new Select().from().table("table1", "t1").leftJoinWithAlias("table2", "t2", "column").build();
		assertEquals("select * from table1 t1 left join table2 t2 on t1.column = t2.column", query);

		query = new Select().from().table("table1").leftJoin("table2", "table1column", "table2column").build();
		assertEquals("select * from table1 left join table2 on table1.table1column = table2.table2column", query);

		query = new Select().from().table("table1").leftJoin("table2", "column").leftJoin("table3", "column").build();
		assertEquals(
				"select * from table1 left join table2 on table1.column = table2.column left join table3 on table1.column = table3.column",
				query);
	}

	@Test
	public void testQueriesWithVariousTableJoins() {
		String query = new Select().from().table("table").innerJoin("inner", "column").outerJoin("outer", "column")
				.leftJoin("left", "column").rightJoin("right", "column").build();
		assertEquals(
				"select * from table inner join inner on table.column = inner.column outer join outer on table.column = outer.column left join left on table.column = left.column right join right on table.column = right.column",
				query);
	}

	@Test
	public void testQueriesWithHavingClause() {
		Select select = new Select();
		select.from().table("table");
		select.having().condition("1=1");
		assertEquals("select * from table having 1=1", select.build());
	}

	@Test
	public void testQueriesWithOrderByClause() {
		Select select = new Select();
		select.from().table("table");
		select.orderBy().column("name");
		assertEquals("select * from table order by name", select.build());

		select.orderBy().asc();
		assertEquals("select * from table order by name asc", select.build());

		select.orderBy().column("age").desc();
		assertEquals("select * from table order by name, age desc", select.build());
	}

	@Test
	public void testQueriesWithGroupByClause() {
		Select select = new Select();
		select.column("name").from().table("table");
		assertEquals("select name from table", select.build());

		// Test automatic group by columns
		select.aggregate("count(*)").from().table("table");
		assertEquals("select name, count(*) from table group by name", select.build());

		// Set explicit group by columns, shouldn't change above query
		select.groupBy().column("name");
		assertEquals("select name, count(*) from table group by name", select.build());
	}

	@Test
	public void testQueryArguments() {
		Select select = new Select();
		select.column("name").from().table("table");
		select.where().condition("1=1").condition("name = ?", "Mary").condition("birthdate between ? and ?",
				"1940-01-01", "2020-12-31");
		assertEquals("select name from table where 1=1 and name = ? and birthdate between ? and ?", select.build());
		List<Object> arguments = select.getArguments();
		assertEquals(3, arguments.size());
		assertEquals("Mary", arguments.get(0));
		assertEquals("1940-01-01", arguments.get(1));
		assertEquals("2020-12-31", arguments.get(2));
	}
}
