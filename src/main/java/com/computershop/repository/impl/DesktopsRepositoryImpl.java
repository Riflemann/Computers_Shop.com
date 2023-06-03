package com.computershop.repository.impl;

import com.computershop.models.Desktop;
import com.computershop.models.enums.TypeDesktops;
import com.computershop.repository.DesktopsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DesktopsRepositoryImpl implements DesktopsRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DesktopsRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Desktop> findAll() {
        List<Desktop> list = null;
        try {
            list = jdbcTemplate.query(
                    "select * from Desktops",
                    this::mapRowToDesktop);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Desktop> findById(String id) {
        String sql = "select id from Desktops where id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sql,
                                                namedParameters,
                                                this::mapRowToDesktop);
    }

    @Override
    public Desktop save(Desktop desktop) {

        SqlRowSet result = findByDesktopAndReturnRowSet(desktop);

//        result.previous();
        if (result.next()) {
            String sqlForUpdateQuantityInDB = "update Desktops set quantity = :quantity where " +
                    "type_desktops = :typeDesktops " +
                    "and series_num = :seriesNum " +
                    "and manufacturer = :manufacturer " +
                    "and cost = :cost ";
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("quantity", desktop.getQuantity() + result.getInt("quantity"))
                    .addValue("typeDesktops", desktop.getTypeDesktops().toString())
                    .addValue("seriesNum", desktop.getSeriesNum())
                    .addValue("manufacturer", desktop.getManufacturer())
                    .addValue("cost", desktop.getCost());

            namedParameterJdbcTemplate.update(sqlForUpdateQuantityInDB, namedParameters);

        } else {
            jdbcTemplate.update(
                    "insert into Desktops (type_desktops, series_num, manufacturer, cost, quantity) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    desktop.getTypeDesktops().toString(),
                    desktop.getSeriesNum(),
                    desktop.getManufacturer(),
                    desktop.getManufacturer(),
                    desktop.getCost(),
                    desktop.getQuantity());;
        }
        return desktop;
    }


    private SqlRowSet findByDesktopAndReturnRowSet(Desktop desktop) {
        String sql = "select * from Desktops where " +
                "type_desktops = :typeDesktops " +
                "and series_num = :seriesNum " +
                "and manufacturer = :manufacturer " +
                "and cost = :cost ";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("typeDesktops", desktop.getTypeDesktops().toString())
                .addValue("seriesNum", desktop.getSeriesNum())
                .addValue("manufacturer", desktop.getManufacturer())
                .addValue("cost", desktop.getCost());
        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, namedParameters);
        if (sqlRowSet.next()) {
            return sqlRowSet;
        } else {
            System.out.println("Указанного товара нет на складе, будет добавлен как новый товар");
        }
        return sqlRowSet;
    }

    private Desktop mapRowToDesktop(ResultSet row, int rowNum) throws SQLException {
        return new Desktop(row.getInt("series_num"),
                row.getString("manufacturer"),
                row.getDouble("cost"),
                row.getInt("quantity"),
                TypeDesktops.valueOf(row.getString("type_desktops")));
    }
}
