package io.github.sqlbuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Update implements Statement {

	protected final String table;
	protected final List<String> columns;
	protected final List<Object> arguments;
	protected final Where where;

	public Update(String table) {
		this.table = table;
		this.columns = new LinkedList<>();
		this.arguments = new LinkedList<>();
		this.where = new Where(this);
	}

	public Update value(String column, Object argument) {
		this.columns.add(column);
		this.arguments.add(argument);
		return this;
	}

	public Where where() {
		return where;
	}

	@Override
	public String build() {
		String columnString = columns.stream().map(column -> column + " = ?").collect(Collectors.joining(", "));
		String whereString = where.whereString();
		return String.format("update %s set %s%s", table, columnString, whereString);
	}

	@Override
	public List<Object> getArguments() {
		LinkedList<Object> allArguments = new LinkedList<>();
		allArguments.addAll(arguments);
		allArguments.addAll(where.getArguments());
		return Collections.unmodifiableList(allArguments);
	}

}
