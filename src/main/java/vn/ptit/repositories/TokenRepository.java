package vn.ptit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ptit.entities.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token,String> {
}
