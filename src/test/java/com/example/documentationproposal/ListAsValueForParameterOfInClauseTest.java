package com.example.documentationproposal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import java.util.Map;

@JdbcTest
@Sql(statements = "insert into persons(name) values ('Joe'), ('Jack'), ('Joe')")
class ListAsValueForParameterOfInClauseTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Test
    void worksWithNamedParameterJdbcTemplate() {
        namedParameterJdbcTemplate.queryForObject(
                "select count(*) from persons where name in (:names)",
                Map.of("names",
                        List.of("Joe", "Jack")),
                Long.class);
    }
    @Test
    void doesNotworkWithJdbcTemplate() {
        jdbcTemplate.queryForObject(
                "select count(*) from persons where name in (?)",
                Long.class,
                List.of("Joe", "Jack"));
    }
}
