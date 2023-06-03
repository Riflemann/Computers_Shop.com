package com.computershop.repository;

import com.computershop.models.HardDisc;

import java.util.List;

public interface HardDiscsRepository {

    List<HardDisc> findAll();

    List<HardDisc> findById(String id);

    HardDisc save(HardDisc hardDisc);
}
