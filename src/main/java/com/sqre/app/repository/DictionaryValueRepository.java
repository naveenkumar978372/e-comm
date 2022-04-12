package com.sqre.app.repository;

import com.sqre.app.model.DictionaryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryValueRepository extends JpaRepository<DictionaryValue, Long> {

    @Query(nativeQuery = true, value = "select * from dictionary_value where distance = :distance and dictionary_id = :dictionaryId")
    DictionaryValue findByDictionaryDAndDistance(long distance, Long dictionaryId);

}
