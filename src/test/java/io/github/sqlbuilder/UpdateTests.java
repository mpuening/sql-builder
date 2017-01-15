package io.github.sqlbuilder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class UpdateTests {
	@Test
	public void testUpdate() {
		Update update = new Update("table");
		update.value("name", "Marianne").value("age", 17);
		update.where().condition("1=1").condition("name = ?", "Mary");
		assertEquals("update table set name = ?, age = ? where 1=1 and name = ?", update.build());
		List<Object> arguments = update.getArguments();
		assertEquals(3, arguments.size());
		assertEquals("Marianne", arguments.get(0));
		assertEquals(17, arguments.get(1));
		assertEquals("Mary", arguments.get(2));
	}
}
