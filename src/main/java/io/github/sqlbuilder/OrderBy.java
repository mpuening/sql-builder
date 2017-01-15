package io.github.sqlbuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

class OrderBy {
	protected final Statement statement;
	protected final Set<String> columns;
	protected String direction;

	OrderBy(Statement statement) {
		this.statement = statement;
		this.columns = new LinkedHashSet<>();
		this.direction = "";
	}

	public OrderBy column(String column) {
		columns.add(column);
		return this;
	}

	public OrderBy asc() {
		direction = " asc";
		return this;
	}

	public OrderBy desc() {
		direction = " desc";
		return this;
	}

	public String build() {
		return statement.build();
	}

	String orderByString() {
		String orderByString = columns.stream().collect(Collectors.joining(", "));
		if (!orderByString.isEmpty()) {
			orderByString = " order by " + orderByString + direction;
		}
		return orderByString;
	}
}
