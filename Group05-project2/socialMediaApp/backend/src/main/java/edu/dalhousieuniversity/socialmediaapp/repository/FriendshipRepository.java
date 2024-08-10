package edu.dalhousieuniversity.socialmediaapp.repository;

import edu.dalhousieuniversity.socialmediaapp.model.Friendship;
import edu.dalhousieuniversity.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    List<Friendship> findByUser(User user);
    List<Friendship> findByFriend(User friend);
    Friendship findByUserAndFriend(User user, User friend);
    List<Friendship> findByUserOrFriend(User user, User friend);

}
