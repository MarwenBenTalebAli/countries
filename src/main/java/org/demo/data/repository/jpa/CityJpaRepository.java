package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.CityEntity;

/**
 * Repository : City.
 */
public interface CityJpaRepository extends PagingAndSortingRepository<CityEntity, Integer> {

}
