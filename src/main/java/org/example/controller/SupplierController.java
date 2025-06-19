package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Supplier;
import org.example.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/supplier/api/v1")
public class SupplierController {
    private final SupplierService supplierService;
    @PostMapping("/save")
    public ResponseEntity<String> saveSupplier(@RequestBody Supplier supplier){
        try{
            supplierService.saveSupplier(supplier);
            return new ResponseEntity<>("Supplier saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
