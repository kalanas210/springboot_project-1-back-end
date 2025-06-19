package org.example.service;

import org.example.dto.Supplier;
import org.springframework.stereotype.Service;

@Service
public interface SupplierService {
    void saveSupplier(Supplier supplier);
}
