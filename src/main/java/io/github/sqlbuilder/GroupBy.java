package io.github.sqlbuilder;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class GroupBy {
	protected final Statement statement;
	protected final Set<String> columns;

	GroupBy(Statement statement) {
		this.statement = statement;
		this.columns = new LinkedHashSet<>();
	}

	public GroupBy column(String column) {
		columns.add(column);
		return this;
	}

	public String build() {
		return statement.build();
	}

	String groupByString(List<Column> allColumns) {
		List<String> groupByColumns = this.columns.stream().collect(Collectors.toList());
		if (columns.isEmpty()) {
			// Check if we should automatically add non-aggregate columns
			List<String> nonAggregateColumns = allColumns.stream().filter(Column::isNotAggregate)
					.map(Column::groupByName).collect(Collectors.toList());
			if (nonAggregateColumns.size() != allColumns.size()) {
				// User didn't bother to add group by columns, so let's do it
				// for them
				groupByColumns = nonAggregateColumns;
			}
		}
		String groupByString = groupByColumns.stream().collect(Collectors.joining(", "));
		if (!groupByString.isEmpty()) {
			groupByString = " group by " + groupByString;
		}
		return groupByString;
	}
}
