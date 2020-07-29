package com.redisexample.repository;

import com.redisexample.entity.RedDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author licha
 * @since 2020/7/29 11:12
 */
public interface RedDetailRepository extends JpaRepository<RedDetail, Integer> {
}

