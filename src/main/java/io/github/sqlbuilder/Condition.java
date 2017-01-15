package io.github.sqlbuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Condition {

	private final String condition;
	private final List<Object> arguments;

	public Condition(String condition) {
		this.condition = condition;
		this.arguments = Collections.emptyList();
	}

	public Condition(String condition, Object... arguments) {
		super();
		this.condition = condition;
		this.arguments = Arrays.asList(arguments);
	}

	String conditionString() {
		return condition;
	}

	List<Object> getArguments() {
		return arguments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
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
		Condition other = (Condition) obj;
		if (arguments == null) {
			if (other.arguments != null) {
				return false;
			}
		} else if (!arguments.equals(other.arguments)) {
			return false;
		}
		if (condition == null) {
			if (other.condition != null) {
				return false;
			}
		} else if (!condition.equals(other.condition)) {
			return false;
		}
		return true;
	}

}
