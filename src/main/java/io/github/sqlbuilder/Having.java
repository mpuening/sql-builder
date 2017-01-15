package io.github.sqlbuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Having {

	protected final Statement statement;
	protected final Set<Condition> conditions;

	Having(Statement statement) {
		this.statement = statement;
		this.conditions = new LinkedHashSet<>();
	}

	public Having condition(String condition) {
		conditions.add(new Condition(condition));
		return this;
	}

	public Having condition(String condition, Object... arguments) {
		conditions.add(new Condition(condition, arguments));
		return this;
	}

	public String build() {
		return statement.build();
	}

	String havingString() {
		String conditionString = conditions.stream().map(Condition::conditionString)
				.collect(Collectors.joining(" and "));
		if (!conditionString.isEmpty()) {
			conditionString = " having " + conditionString;
		}
		return conditionString;
	}
}
