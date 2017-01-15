package io.github.sqlbuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Insert implements Statement {

	protected final String table;
	protected final List<String> columns;
	protected final List<Object> arguments;

	public Insert(String table) {
		this.table = table;
		this.columns = new LinkedList<>();
		this.arguments = new LinkedList<>();
	}

	public Insert column(String column, Object argument) {
		this.columns.add(column);
		this.arguments.add(argument);
		return this;
	}

	@Override
	public String build() {
		String columnString = columns.stream().collect(Collectors.joining(", "));
		String valueString = columns.stream().map(column -> "?").collect(Collectors.joining(", "));
		return String.format("insert into %s (%s) values (%s)", table, columnString, valueString);
	}

	@Override
	public List<Object> getArguments() {
		return Collections.unmodifiableList(arguments);
	}

}
