package io.github.sqlbuilder;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Where {

	protected final Statement statement;
	protected final Set<Condition> conditions;

	Where(Statement statement) {
		this.statement = statement;
		this.conditions = new LinkedHashSet<>();
	}

	public Where condition(String condition) {
		conditions.add(new Condition(condition));
		return this;
	}

	public Where condition(String condition, Object... arguments) {
		conditions.add(new Condition(condition, arguments));
		return this;
	}

	public String build() {
		return statement.build();
	}

	String whereString() {
		String conditionString = conditions.stream().map(Condition::conditionString)
				.collect(Collectors.joining(" and "));
		if (!conditionString.isEmpty()) {
			conditionString = " where " + conditionString;
		}
		return conditionString;
	}

	public List<Object> getArguments() {
		return conditions.stream().map(Condition::getArguments).flatMap(l -> l.stream()).collect(Collectors.toList());
	}

}
