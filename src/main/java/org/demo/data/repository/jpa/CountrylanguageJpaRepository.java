package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.CountrylanguageEntity;
import org.demo.bean.jpa.CountrylanguageEntityKey;

/**
 * Repository : Countrylanguage.
 */
public interface CountrylanguageJpaRepository extends PagingAndSortingRepository<CountrylanguageEntity, CountrylanguageEntityKey> {

}
