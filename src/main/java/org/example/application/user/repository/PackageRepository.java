package org.example.application.user.repository;

import org.example.application.user.model.Package;
import org.example.application.user.model.Session;

import java.util.List;

public interface PackageRepository {

    List<Package> findAll();

    //Package findByToken(String token);

    Package save(Package cardspackage);

    Package acquire(String username);

    int deleteById(int id);

    Package getFirstAvailablePackage();
}
