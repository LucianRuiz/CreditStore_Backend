package com.creditstore.CreditStore.accounts.repository;

import com.creditstore.CreditStore.shared.formulas.DatosSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DatosSalidaRepository extends JpaRepository<DatosSalida, Integer> {

    List<DatosSalida> findAllByAccount_Id(Integer accountId);
}

