package com.computershop.service;

import com.computershop.models.HardDisc;

import java.util.List;

public interface HardDiscsService {
    HardDisc save(HardDisc hardDisc);

    HardDisc edit(HardDisc hardDisc, String id);

    List<HardDisc> getAll();

    HardDisc getById(String id);
}
