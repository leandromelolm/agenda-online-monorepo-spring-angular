package com.lm.myagenda.repositories;

import com.lm.myagenda.models.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM Attendance obj WHERE obj.agenda.id LIKE :id")
    Page<Attendance> findAllByIdAgenda(@Param("id") Long idAgenda, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT EXISTS (SELECT * FROM ATTENDANCE_PROFESSIONAIS WHERE PROFESSIONAIS_ID LIKE :id)", nativeQuery = true)
    boolean existsProfessionalInSomeAttendance(@Param("id") Long id);

    //@Query(value = "SELECT COUNT(0) FROM ATTENDANCE_PROFESSIONAIS WHERE PROFESSIONAIS_ID LIKE :id", nativeQuery = true)

}
