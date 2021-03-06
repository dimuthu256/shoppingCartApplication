package com.org.shoppingcart.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.shoppingcart.core.entities.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer>{

}
