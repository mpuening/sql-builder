package io.github.sqlbuilder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class InsertTests {

	@Test
	public void testInsert() {
		Insert insert = new Insert("table").column("column1", "value1").column("column2", "value2")
				.column("column3", "value3").column("column4", "value4");
		assertEquals("insert into table (column1, column2, column3, column4) values (?, ?, ?, ?)", insert.build());
		List<Object> arguments = insert.getArguments();
		assertEquals(4, arguments.size());
		assertEquals("value1", arguments.get(0));
		assertEquals("value2", arguments.get(1));
		assertEquals("value3", arguments.get(2));
		assertEquals("value4", arguments.get(3));
	}
}
