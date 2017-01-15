package io.github.sqlbuilder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class DeleteTests {

	@Test
	public void testDelete() {
		Delete delete = new Delete("table");
		delete.where().condition("1=1").condition("name = ?", "Mary");
		assertEquals("delete from table where 1=1 and name = ?", delete.build());
		List<Object> arguments = delete.getArguments();
		assertEquals(1, arguments.size());
		assertEquals("Mary", arguments.get(0));
	}
}
