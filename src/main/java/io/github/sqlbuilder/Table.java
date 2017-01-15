package io.github.sqlbuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Table {

	protected final From from;
	protected final String name;
	protected final String alias;
	protected final Set<Join> joins;

	Table(From from, String name, String alias) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("table cannot be empty");
		}
		this.from = from;
		this.name = name;
		this.alias = alias;
		this.joins = new LinkedHashSet<>();
	}

	Table(From from, String name) {
		this(from, name, null);
	}

	public Table table(String name) {
		return from.table(name);
	}

	public Table join(String right, String column) {
		return sameColumnJoin(right, column, "join");
	}

	public Table innerJoin(String right, String column) {
		return sameColumnJoin(right, column, "inner join");
	}

	public Table leftJoin(String right, String column) {
		return sameColumnJoin(right, column, "left join");
	}

	public Table rightJoin(String right, String column) {
		return sameColumnJoin(right, column, "right join");
	}

	public Table outerJoin(String right, String column) {
		return sameColumnJoin(right, column, "outer join");
	}

	protected Table sameColumnJoin(String right, String column, String type) {
		if (column == null || column.isEmpty()) {
			throw new IllegalArgumentException("join column cannot be empty");
		}
		String left = this.name;
		String condition = String.format("on %s.%s = %s.%s", left, column, right, column);
		Join join = new Join(this.from, this, new Table(this.from, right), condition, type);
		joins.add(join);
		return this;
	}

	public Table joinWithAlias(String right, String alias, String column) {
		return sameColumnJoinWithAlias(right, alias, column, "join");
	}

	public Table innerJoinWithAlias(String right, String alias, String column) {
		return sameColumnJoinWithAlias(right, alias, column, "inner join");
	}

	public Table leftJoinWithAlias(String right, String alias, String column) {
		return sameColumnJoinWithAlias(right, alias, column, "left join");
	}

	public Table rightJoinWithAlias(String right, String alias, String column) {
		return sameColumnJoinWithAlias(right, alias, column, "right join");
	}

	public Table outerJoinWithAlias(String right, String alias, String column) {
		return sameColumnJoinWithAlias(right, alias, column, "outer join");
	}

	protected Table sameColumnJoinWithAlias(String right, String alias, String column, String type) {
		if (column == null || column.isEmpty()) {
			throw new IllegalArgumentException("join column cannot be empty");
		}
		String left = this.alias;
		String condition = String.format("on %s.%s = %s.%s", left, column, alias, column);
		Join join = new Join(this.from, this, new Table(this.from, right, alias), condition, type);
		joins.add(join);
		return this;
	}

	public Table join(String right, String leftColumn, String rightColumn) {
		return differentColumnJoin(right, leftColumn, rightColumn, "join");
	}

	public Table innerJoin(String right, String leftColumn, String rightColumn) {
		return differentColumnJoin(right, leftColumn, rightColumn, "inner join");
	}

	public Table leftJoin(String right, String leftColumn, String rightColumn) {
		return differentColumnJoin(right, leftColumn, rightColumn, "left join");
	}

	public Table rightJoin(String right, String leftColumn, String rightColumn) {
		return differentColumnJoin(right, leftColumn, rightColumn, "right join");
	}

	public Table outerJoin(String right, String leftColumn, String rightColumn) {
		return differentColumnJoin(right, leftColumn, rightColumn, "outer join");
	}

	protected Table differentColumnJoin(String right, String leftColumn, String rightColumn, String type) {
		if (leftColumn == null || leftColumn.isEmpty()) {
			throw new IllegalArgumentException("table column cannot be empty");
		}
		if (rightColumn == null || rightColumn.isEmpty()) {
			throw new IllegalArgumentException("join column cannot be empty");
		}
		String left = this.name;
		String condition = String.format("on %s.%s = %s.%s", left, leftColumn, right, rightColumn);
		Join join = new Join(this.from, this, new Table(this.from, right), condition, type);
		joins.add(join);
		return this;
	}

	public Table joinWithAlias(String right, String alias, String leftColumn, String rightColumn) {
		return differentColumnJoinWithAlias(right, alias, leftColumn, rightColumn, "join");
	}

	public Table innerJoinWithAlias(String right, String alias, String leftColumn, String rightColumn) {
		return differentColumnJoinWithAlias(right, alias, leftColumn, rightColumn, "inner join");
	}

	public Table leftJoinWithAlias(String right, String alias, String leftColumn, String rightColumn) {
		return differentColumnJoinWithAlias(right, alias, leftColumn, rightColumn, "left join");
	}

	public Table rightJoinWithAlias(String right, String alias, String leftColumn, String rightColumn) {
		return differentColumnJoinWithAlias(right, alias, leftColumn, rightColumn, "right join");
	}

	public Table outerJoinWithAlias(String right, String alias, String leftColumn, String rightColumn) {
		return differentColumnJoinWithAlias(right, alias, leftColumn, rightColumn, "outer join");
	}

	protected Table differentColumnJoinWithAlias(String right, String alias, String leftColumn, String rightColumn,
			String type) {
		if (leftColumn == null || leftColumn.isEmpty()) {
			throw new IllegalArgumentException("table column cannot be empty");
		}
		if (rightColumn == null || rightColumn.isEmpty()) {
			throw new IllegalArgumentException("join column cannot be empty");
		}
		String left = this.alias;
		String condition = String.format("on %s.%s = %s.%s", left, leftColumn, alias, rightColumn);

		Join join = new Join(this.from, this, new Table(this.from, right, alias), condition, type);
		joins.add(join);
		return this;
	}

	public Table joinCondition(String right, String condition) {
		return specifiedConditionJoin(right, condition, "join");
	}

	public Table innerJoinCondition(String right, String condition) {
		return sameColumnJoin(right, condition, "inner join");
	}

	public Table leftJoinCondition(String right, String condition) {
		return sameColumnJoin(right, condition, "left join");
	}

	public Table rightJoinCondition(String right, String condition) {
		return sameColumnJoin(right, condition, "right join");
	}

	public Table outerJoinCondition(String right, String condition) {
		return sameColumnJoin(right, condition, "outer join");
	}

	protected Table specifiedConditionJoin(String right, String condition, String type) {
		if (condition == null || condition.isEmpty()) {
			throw new IllegalArgumentException("condition cannot be empty");
		}
		Join join = new Join(this.from, this, new Table(this.from, right), "on " + condition, type);
		joins.add(join);
		return this;
	}

	public From and() {
		return this.from;
	}

	public Where where() {
		return from.where();
	}

	public String build() {
		return from.build();
	}

	String tableString() {
		String aliasString = alias != null ? " " + alias : "";
		return name + aliasString + joins.stream().map(Join::joinString).collect(Collectors.joining());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
