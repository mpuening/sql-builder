package io.github.sqlbuilder;

class Column {

	protected final String name;
	protected final String alias;
	protected final boolean isAggregate;

	Column(String name) {
		this(name, null, false);
	}

	Column(String name, boolean isAggregate) {
		this(name, null, isAggregate);
	}

	Column(String name, String alias) {
		this(name, alias, false);
	}

	Column(String name, String alias, boolean isAggregate) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("column name cannot be empty");
		}
		this.name = name;
		this.alias = alias;
		this.isAggregate = isAggregate;
	}

	boolean isNotAggregate() {
		return !this.isAggregate;
	}

	String groupByName() {
		return ((alias != null) ? alias : name);
	}

	String columnString() {
		return String.format("%s%s", name, ((alias != null) ? " as " + alias : ""));
	}

}
