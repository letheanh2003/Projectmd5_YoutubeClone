package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Users;

@Repository
public interface IUserRepository extends JpaRepository<Users,Long> {
    Users findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
}
