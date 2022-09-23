package kz.bitlab.spring.crm.repository;

import kz.bitlab.spring.crm.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {
}
