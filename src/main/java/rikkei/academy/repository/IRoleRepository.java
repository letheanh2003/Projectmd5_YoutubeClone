package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.Roles;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Roles,Long> {

    Optional<Roles> findByRoleName(RoleName roleName);
}
