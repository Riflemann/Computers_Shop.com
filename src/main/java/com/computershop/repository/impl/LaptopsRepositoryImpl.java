package com.computershop.repository.impl;

import com.computershop.models.HardDisc;
import com.computershop.models.Laptop;
import com.computershop.models.enums.Diagonal;
import com.computershop.repository.LaptopsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LaptopsRepositoryImpl implements LaptopsRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LaptopsRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Laptop> findAll() {
        List<Laptop> list = null;
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
    public List<Laptop> findById(String id) {
        String sql = "select id from Laptops where id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sql,
                namedParameters,
                this::mapRowToDesktop);
    }

    @Override
    public Laptop save(Laptop laptop) {

        SqlRowSet result = findByDesktopAndReturnRowSet(laptop);

//        result.previous();
        if (result.next()) {
            String sqlForUpdateQuantityInDB = "update Laptops set quantity = :quantity where " +
                    "diagonal = :diagonal " +
                    "and series_num = :seriesNum " +
                    "and manufacturer = :manufacturer " +
                    "and cost = :cost ";
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("quantity", laptop.getQuantity() + result.getInt("quantity"))
                    .addValue("diagonal", laptop.getDiagonal().toString())
                    .addValue("seriesNum", laptop.getSeriesNum())
                    .addValue("manufacturer", laptop.getManufacturer())
                    .addValue("cost", laptop.getCost());

            namedParameterJdbcTemplate.update(sqlForUpdateQuantityInDB, namedParameters);

        } else {
            jdbcTemplate.update(
                    "insert into Laptops (diagonal, series_num, manufacturer, cost, quantity) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    laptop.getDiagonal().toString(),
                    laptop.getSeriesNum(),
                    laptop.getManufacturer(),
                    laptop.getManufacturer(),
                    laptop.getCost(),
                    laptop.getQuantity());;
        }
        return laptop;
    }


    private SqlRowSet findByDesktopAndReturnRowSet(Laptop laptop) {
        String sql = "select * from Laptops where " +
                "diagonal = :diagonal " +
                "and series_num = :seriesNum " +
                "and manufacturer = :manufacturer " +
                "and cost = :cost ";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("diagonal", laptop.getDiagonal().toString())
                .addValue("seriesNum", laptop.getSeriesNum())
                .addValue("manufacturer", laptop.getManufacturer())
                .addValue("cost", laptop.getCost());
        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, namedParameters);
        if (sqlRowSet.next()) {
            return sqlRowSet;
        } else {
            System.out.println("Указанного товара нет на складе, будет добавлен как новый товар " + laptop);
        }
        return sqlRowSet;
    }

    private Laptop mapRowToDesktop(ResultSet row, int rowNum) throws SQLException {
        return new Laptop(row.getInt("series_num"),
                row.getString("manufacturer"),
                row.getDouble("cost"),
                row.getInt("quantity"),
                Diagonal.valueOf(row.getString("diagonal")));
    }
}
