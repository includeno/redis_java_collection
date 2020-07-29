package com.redisexample.repository;

import com.redisexample.entity.RedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedRecordRepository extends JpaRepository<RedRecord,Integer> {
}
