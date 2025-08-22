package com.inventra.Repository;


import com.inventra.Models.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListedTokenRespository extends JpaRepository<BlackListedToken, Long> {
    boolean existsByToken(String token);
}
