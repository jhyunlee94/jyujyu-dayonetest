package com.jyujuy.dayonetest.repository;

import com.jyujuy.dayonetest.model.StudentFail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentFailRepository extends JpaRepository<StudentFail, Long> {
}
