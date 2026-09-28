package com.telemedicine.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface defining standard CRUD operations.
 * Demonstrates:
 * - Interface Abstraction
 * - Generics (<T, ID>)
 * - Loose Coupling between service layer and data storage
 */
public interface Repository<T, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean existsById(ID id);

    boolean deleteById(ID id);
}
