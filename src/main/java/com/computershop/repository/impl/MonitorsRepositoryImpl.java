package com.computershop.repository.impl;

import com.computershop.models.Monitor;
import com.computershop.models.enums.Diagonal;
import com.computershop.repository.MonitorsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MonitorsRepositoryImpl implements MonitorsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MonitorsRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Monitor> findAll() {
        List<Monitor> list = null;
        try {
            list = jdbcTemplate.query(
                    "select * from Laptops",
                    this::mapRowToDesktop);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Monitor> findById(String id) {
        String sql = "select id from Laptops where id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sql,
                namedParameters,
                this::mapRowToDesktop);
    }

    @Override
    public Monitor save(Monitor monitor) {

        SqlRowSet result = findByDesktopAndReturnRowSet(monitor);

//        result.previous();
        if (result.next()) {
            String sqlForUpdateQuantityInDB = "update Laptops set quantity = :quantity where " +
                    "diagonal = :diagonal " +
                    "and series_num = :seriesNum " +
                    "and manufacturer = :manufacturer " +
                    "and cost = :cost ";
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("quantity", monitor.getQuantity() + result.getInt("quantity"))
                    .addValue("diagonal", monitor.getDiagonal().toString())
                    .addValue("seriesNum", monitor.getSeries_num())
                    .addValue("manufacturer", monitor.getManufacturer())
                    .addValue("cost", monitor.getCost());

            namedParameterJdbcTemplate.update(sqlForUpdateQuantityInDB, namedParameters);

        } else {
            jdbcTemplate.update(
                    "insert into Laptops (diagonal, series_num, manufacturer, cost, quantity) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    monitor.getDiagonal().toString(),
                    monitor.getSeries_num(),
                    monitor.getManufacturer(),
                    monitor.getManufacturer(),
                    monitor.getCost(),
                    monitor.getQuantity());;
        }
        return monitor;
    }


    private SqlRowSet findByDesktopAndReturnRowSet(Monitor monitor) {
        String sql = "select * from Laptops where " +
                "diagonal = :diagonal " +
                "and series_num = :seriesNum " +
                "and manufacturer = :manufacturer " +
                "and cost = :cost ";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("diagonal", monitor.getDiagonal().toString())
                .addValue("seriesNum", monitor.getSeries_num())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("cost", monitor.getCost());
        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, namedParameters);
        if (sqlRowSet.next()) {
            return sqlRowSet;
        } else {
            System.out.println("Указанного товара нет на складе, будет добавлен как новый товар " + monitor);
        }
        return sqlRowSet;
    }

    private Monitor mapRowToDesktop(ResultSet row, int rowNum) throws SQLException {
        return new Monitor(row.getInt("series_num"),
                row.getString("manufacturer"),
                row.getDouble("cost"),
                row.getInt("quantity"),
                Diagonal.valueOf(row.getString("diagonal")));
    }
}
