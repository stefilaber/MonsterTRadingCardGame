package org.example.application.user.repository;

import org.example.application.user.model.Card;
import org.example.application.user.model.Package;

import java.util.List;

public interface CardRepository {

    List<Card> findAll();

    Card findByID(int id);

    Card save(Card card);

    Card delete();
}
