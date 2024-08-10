package edu.dalhousieuniversity.socialmediaapp.repository;

import edu.dalhousieuniversity.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findByjoinStatus(String pending);
}
