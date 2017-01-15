package io.github.sqlbuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

class From {

	protected final Statement statement;
	protected final Map<String, Table> tables;
	protected final Where where;

	public From(Statement statement, Where where) {
		this.statement = statement;
		this.tables = new LinkedHashMap<>();
		this.where = where;
	}

	public Table table(String name) {
		Table table = tables.get(name);
		if (table == null) {
			table = new Table(this, name);
			tables.put(name, table);
		}
		return table;
	}

	public Table table(String name, String alias) {
		Table table = tables.get(name);
		if (table == null) {
			table = new Table(this, name, alias);
			tables.put(name, table);
		}
		return table;
	}

	public Where where() {
		return where;
	}

	public String build() {
		return statement.build();
	}

	String fromString() {
		String tablesString = tables.values().stream().map(Table::tableString).collect(Collectors.joining(", "));
		if (tablesString.isEmpty()) {
			throw new IllegalArgumentException("no tables have been specified");
		}
		return String.format(" from %s", tablesString);
	}

}
