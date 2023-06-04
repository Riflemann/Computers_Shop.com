//package com.computershop.repository.impl;
//
//import com.computershop.models.Desktops;
//import com.computershop.models.enums.TypeDesktops;
//import com.computershop.repository.jdbc.DesktopsRepositoryJDBC;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
////@Repository
//public class DesktopsRepositoryJDBCImpl implements {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    public DesktopsRepositoryJDBCImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//    }
//
//    @Override
//    public List<Desktops> findAll() {
//        List<Desktops> list = null;
//        try {
//            list = jdbcTemplate.query(
//                    "select * from Desktops",
//                    this::mapRowToDesktop);
//        } catch (DataAccessException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    @Override
//    public Desktops findById(String id) {
//        String sql = "select id from Desktops where id=:id";
//        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
//        return namedParameterJdbcTemplate.queryForObject(
//                sql,
//                namedParameters,
//                this::mapRowToDesktop);
//    }
//
//    @Override
//    public Desktops save(Desktops desktops) {
//
//        SqlRowSet result = findByDesktopAndReturnRowSet(desktops);
//
////        result.previous();
//        if (result.next()) {
//            String sqlForUpdateQuantityInDB = "update Desktops set quantity = :quantity where " +
//                    "type_desktops = :typeDesktops " +
//                    "and series_num = :seriesNum " +
//                    "and manufacturer = :manufacturer " +
//                    "and cost = :cost ";
//            SqlParameterSource namedParameters = new MapSqlParameterSource()
//                    .addValue("quantity", desktops.getQuantity() + result.getInt("quantity"))
//                    .addValue("typeDesktops", desktops.getType_desktops().toString())
//                    .addValue("seriesNum", desktops.getSeries_num())
//                    .addValue("manufacturer", desktops.getManufacturer())
//                    .addValue("cost", desktops.getCost());
//
//            namedParameterJdbcTemplate.update(sqlForUpdateQuantityInDB, namedParameters);
//
//        } else {
//            jdbcTemplate.update(
//                    "insert into Desktops (type_desktops, series_num, manufacturer, cost, quantity) " +
//                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
//                    desktops.getType_desktops().toString(),
//                    desktops.getSeries_num(),
//                    desktops.getManufacturer(),
//                    desktops.getManufacturer(),
//                    desktops.getCost(),
//                    desktops.getQuantity());;
//        }
//        return desktops;
//    }
//
//    @Override
//    public SqlRowSet findByDesktopAndReturnRowSet(Desktops desktops) {
//        String sql = "select * from Desktops where " +
//                "type_desktops = :typeDesktops " +
//                "and series_num = :seriesNum " +
//                "and manufacturer = :manufacturer " +
//                "and cost = :cost ";
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("typeDesktops", desktops.getType_desktops().toString())
//                .addValue("seriesNum", desktops.getSeries_num())
//                .addValue("manufacturer", desktops.getManufacturer())
//                .addValue("cost", desktops.getCost());
//        SqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, namedParameters);
//        if (sqlRowSet.next()) {
//            return sqlRowSet;
//        } else {
//            System.out.println("Указанного товара нет на складе, будет добавлен как новый товар");
//            return sqlRowSet;
//        }
//    }
//
//    @Override
//    public void updateQuantity(int id, int quantity) {
//        String sql = "update Desktops set quantity = :quantity where id = :id ";
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("quantity", quantity)
//                .addValue("id", id);
//        namedParameterJdbcTemplate.update(sql, namedParameters);
//    }
//
//    private Desktops mapRowToDesktop(ResultSet row, int rowNum) throws SQLException {
//        return new Desktops(row.getInt("series_num"),
//                row.getString("manufacturer"),
//                row.getDouble("cost"),
//                row.getInt("quantity"),
//                TypeDesktops.valueOf(row.getString("type_desktops")));
//    }
//}
