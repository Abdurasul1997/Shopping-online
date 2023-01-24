package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.domains.Uploads;

public interface UploadsRepository extends JpaRepository<Uploads,Long> {

}
