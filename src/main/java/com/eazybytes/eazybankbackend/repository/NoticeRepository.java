package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {

    // JPQL che restituisce gli avvisi la cui finestra di validità contiene la data odierna (usa CURDATE() MySQL)
    @Query(value = "from Notice n where CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<Notice> findAllActiveNotices();

}
