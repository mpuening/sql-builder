package io.github.sqlbuilder;

import java.util.Collections;
import java.util.List;

public class Delete implements Statement {

	protected final String table;
	protected final Where where;

	public Delete(String table) {
		this.table = table;
		this.where = new Where(this);
	}

	public Where where() {
		return where;
	}

	@Override
	public String build() {
		String whereString = where.whereString();
		return String.format("delete from %s%s", table, whereString);
	}

	@Override
	public List<Object> getArguments() {
		return Collections.unmodifiableList(where.getArguments());
	}

}
