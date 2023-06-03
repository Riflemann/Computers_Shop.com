package com.computershop.repository;

import com.computershop.models.Desktop;

import java.util.List;
import java.util.Optional;

public interface DesktopsRepository {
    List<Desktop> findAll();

    List<Desktop> findById(String id);

    Desktop save(Desktop desktop);
}
