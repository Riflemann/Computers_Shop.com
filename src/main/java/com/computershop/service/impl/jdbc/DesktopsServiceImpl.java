package com.computershop.service.impl.jdbc;

import com.computershop.models.Desktops;
import com.computershop.repository.jdbc.DesktopsRepositoryJDBC;
import com.computershop.service.DesktopsService;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

//@Service
public class DesktopsServiceImpl implements DesktopsService {
    private final DesktopsRepositoryJDBC desktopsRepositoryJDBC;

    public DesktopsServiceImpl(DesktopsRepositoryJDBC desktopsRepositoryJDBC) {
        this.desktopsRepositoryJDBC = desktopsRepositoryJDBC;
    }

    @Override
    public List<Desktops> getAll() {
        return desktopsRepositoryJDBC.findAll();
    }

    @Override
    public Desktops getById(String id) {
        return desktopsRepositoryJDBC.findById(id);
    }

    @Override
    public Desktops save(Desktops desktops) {
        SqlRowSet sqlRowSet = desktopsRepositoryJDBC.findByDesktopAndReturnRowSet(desktops);
        sqlRowSet.previous();
        int id = sqlRowSet.getInt("id");
        if (id != 0) {
            desktopsRepositoryJDBC.save(desktops);
        } else {
            int newQuantity = desktops.getQuantity() + sqlRowSet.getInt("quantity");
            desktopsRepositoryJDBC.updateQuantity(id, newQuantity);
        }
        return desktops;
    }

    @Override
    public Desktops edit(Desktops desktops, String id) {
        return null;
    }

//    public Desktops editDesktop() {
//
//    }


}
