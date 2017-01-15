package io.github.sqlbuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Select implements Statement {

	protected final List<Column> columns;
	protected final From from;
	protected final Where where;
	protected final GroupBy groupBy;
	protected final Having having;
	protected final OrderBy orderBy;

	public Select() {
		this.columns = new LinkedList<>();
		this.from = new From(this, new Where(this));
		this.where = from.where();
		this.groupBy = new GroupBy(this);
		this.having = new Having(this);
		this.orderBy = new OrderBy(this);
	}

	public Select column(String name) {
		columns.add(new Column(name));
		return this;
	}

	public Select column(String name, String alias) {
		columns.add(new Column(name, alias));
		return this;
	}

	public Select aggregate(String name) {
		columns.add(new Column(name, true));
		return this;
	}

	public Select aggregate(String name, String alias) {
		columns.add(new Column(name, alias, true));
		return this;
	}

	public From from() {
		return from;
	}

	public Where where() {
		return where;
	}

	public GroupBy groupBy() {
		return groupBy;
	}

	public Having having() {
		return having;
	}

	public OrderBy orderBy() {
		return orderBy;
	}

	@Override
	public String build() {
		String columnString = columns.stream().map(Column::columnString).collect(Collectors.joining(", "));
		if (columnString.isEmpty()) {
			columnString = "*";
		}
		String fromString = from.fromString();
		String whereString = where.whereString();
		String groupByString = groupBy.groupByString(this.columns);
		String havingString = having.havingString();
		String orderByString = orderBy.orderByString();
		return String.format("select %s%s%s%s%s%s", columnString, fromString, whereString, groupByString, havingString,
				orderByString);
	}

	@Override
	public List<Object> getArguments() {
		return Collections.unmodifiableList(where.getArguments());
	}
}
