package com.api.UDEE.repository;

import com.api.UDEE.domain.Measurement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    @Query(value =
            "SELECT mea.*\n "+
                    "from measurements mea"+
                    " join meters m "+
                    " on mea.id_meter = m.id_meter "+
                    " join address ad "+
                    " on m.id_address = ad.address_id "+
                    " join usuarios user "+
                    " on ad.id_user = user.id_user "+
                    " where :id_user = user.id_user "+
                    " and mea.date between :since and :until ",
    nativeQuery = true)
    List<Measurement> findByMeasurementsBetweenDateByUser(@Param("id_user")int id_user, @Param("since") Date since, @Param("until") Date until, Pageable pageable);

    @Query(value =
            "SELECT *\n "+
                    "from measurements mea"+
                    " join meters m "+
                    " on mea.id_meter = m.id_meter "+
                    " join address ad "+
                    " on m.id_address = ad.address_id "+
                    " join usuarios user "+
                    " on ad.id_user = user.id_user "+
                    " where mea.date between :since and :until ",
            nativeQuery = true)
    List<Measurement> findByMeasurementBetweenDates(@Param("from") Date since, @Param("until") Date until);

    @Query(value = "CALL measurementsByDates(:idAddress,:from ,:to )", nativeQuery = true)
    List<Measurement> measurementsByDates(Integer idAddress, Date from, Date to);

}
