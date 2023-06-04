package com.computershop.repository.impl;

import com.computershop.models.HardDisc;
import com.computershop.models.enums.HardDriveVolumes;
import com.computershop.repository.HardDiscsRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HardDiscsRepositoryImpl implements HardDiscsRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public HardDiscsRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<HardDisc> findAll() {
        List<HardDisc> list = null;
        try {
            list = jdbcTemplate.query(
                    "select * from Hard_discs",
                    this::mapRowToDesktop);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<HardDisc> findById(String id) {
        String sql = "select id from Hard_discs where id=:id";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(sql,
                namedParameters,
                this::mapRowToDesktop);
    }

    @Override
    public HardDisc save(HardDisc hardDisc) {

        SqlRowSet result = findByDesktopAndReturnRowSet(hardDisc);

//        result.previous();
        if (result.next()) {
            String sqlForUpdateQuantityInDB = "update Hard_discs set quantity = :quantity where " +
                    "hard_drive_volumes = :hard_drive_volumes " +
                    "and series_num = :seriesNum " +
                    "and manufacturer = :manufacturer " +
                    "and cost = :cost ";
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("quantity", hardDisc.getQuantity() + result.getInt("quantity"))
                    .addValue("hard_drive_volumes", hardDisc.getHardDriveVolumes().toString())
                    .addValue("seriesNum", hardDisc.getSeries_num())
                    .addValue("manufacturer", hardDisc.getManufacturer())
                    .addValue("cost", hardDisc.getCost());

            namedParameterJdbcTemplate.update(sqlForUpdateQuantityInDB, namedParameters);

        } else {
            jdbcTemplate.update(
                    "insert into Hard_discs (hard_drive_volumes, series_num, manufacturer, cost, quantity) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    hardDisc.getHardDriveVolumes().toString(),
                    hardDisc.getSeries_num(),
                    hardDisc.getManufacturer(),
                    hardDisc.getManufacturer(),
                    hardDisc.getCost(),
                    hardDisc.getQuantity());;
        }
        return hardDisc;
    }


    private SqlRowSet findByDesktopAndReturnRowSet(HardDisc hardDisc) {
        String sql = "select * from Hard_discs where " +
                "hard_drive_volumes = :hardDriveVolumes " +
                "and series_num = :seriesNum " +
                "and manufacturer = :manufacturer " +
                "and cost = :cost ";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hardDriveVolumes", hardDisc.getHardDriveVolumes().toString())
                .addValue("seriesNum", hardDisc.getSeries_num())
                .addValue("manufacturer", hardDisc.getManufacturer())
                .addValue("cost", hardDisc.getCost());
        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, namedParameters);
        if (sqlRowSet.next()) {
            return sqlRowSet;
        } else {
            System.out.println("Указанного товара нет на складе, будет добавлен как новый товар " + hardDisc);
        }
        return sqlRowSet;
    }

    private HardDisc mapRowToDesktop(ResultSet row, int rowNum) throws SQLException {
        return new HardDisc(row.getInt("series_num"),
                row.getString("manufacturer"),
                row.getDouble("cost"),
                row.getInt("quantity"),
                HardDriveVolumes.valueOf(row.getString("hard_drive_volumes")));
    }
}
