package com.computershop.service;

import com.computershop.models.Desktops;

import java.util.List;

public interface DesktopsService {
    Desktops save(Desktops desktops);

    Desktops edit(Desktops desktops, String id);

    List<Desktops> getAll();

    Desktops getById(String id);
}
