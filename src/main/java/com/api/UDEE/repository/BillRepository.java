package com.api.UDEE.repository;

import com.api.UDEE.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

    @Query(value = "CALL allBillUnpaidByUserAndAddress(:idUser,:idAddress)", nativeQuery = true)
    List<Bill> allBillUnpaidByUserAndAddress(Integer idUser, Integer idAddress);

    @Query(value = "CALL allBillsByDates(:idUser,:since,:until)", nativeQuery = true)
    List<Bill> allBillsByDates(Integer idUser, Date since, Date until);


}
