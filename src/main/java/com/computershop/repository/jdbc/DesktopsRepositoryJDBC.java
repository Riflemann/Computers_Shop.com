package com.computershop.repository.jdbc;

import com.computershop.models.Desktops;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface DesktopsRepositoryJDBC {
    List<Desktops> findAll();

    Desktops findById(String id);

    Desktops save(Desktops desktops);

    SqlRowSet findByDesktopAndReturnRowSet(Desktops desktops);

    void updateQuantity(int id, int quantity);
}
