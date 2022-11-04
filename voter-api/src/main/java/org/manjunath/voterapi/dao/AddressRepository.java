package org.manjunath.voterapi.dao;

import org.manjunath.voterapi.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Page<Address> findByTallukIgnoreCase(String talluk, Pageable page);

    Page<Address> findByDistrictIgnoreCase(String district, Pageable page);

    Page<Address> findByStateIgnoreCase(String state, Pageable page);
}
