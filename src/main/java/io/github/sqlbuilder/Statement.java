package io.github.sqlbuilder;

import java.util.List;

public interface Statement {

	String build();

	List<Object> getArguments();
}
