package com.fanap.bankservicesystem.testcase;

import com.fanap.bankservicesystem.repository.BankAccountEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Week3 {

    public static void testCase() {
        testPersist();
    }

    private static void testPersist() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankServiceSystem");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setAccountNumber("1");
        bankAccountEntity.setAccountHolderName("Ali");
        bankAccountEntity.setBalance(5000d);

        em.persist(bankAccountEntity);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }

}
