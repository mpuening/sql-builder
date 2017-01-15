package io.github.sqlbuilder;

class Join {

	protected final Table left;
	protected final Table right;

	protected String condition;
	protected String type;

	Join(From from, Table left, Table right, String condition, String type) {
		if (left == null || left.name == null || left.name.isEmpty()) {
			throw new IllegalArgumentException("table cannot be empty");
		}
		if (right == null || right.name == null || right.name.isEmpty()) {
			throw new IllegalArgumentException("join table cannot be empty");
		}
		if (condition == null || condition.isEmpty()) {
			throw new IllegalArgumentException("table join condition cannot be empty");
		}
		this.left = left;
		this.right = right;
		this.condition = condition;
		this.type = type;
	}

	String joinString() {
		String table = right.alias != null ? right.name + " " + right.alias : right.name;
		return String.format(" %s %s %s", type, table, condition);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Join other = (Join) obj;
		if (condition == null) {
			if (other.condition != null) {
				return false;
			}
		} else if (!condition.equals(other.condition)) {
			return false;
		}
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
}
