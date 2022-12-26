package org.example.application.user.repository;

import org.example.application.user.model.Card;
import org.example.application.user.model.CardType;
import org.example.application.user.model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.example.server.DatabaseInitializer.conn;

public class CardDataBaseRepository implements CardRepository{

    @Override
    public List<Card> findAll() {
        String cardsFindAllSql = "SELECT * FROM cards";
        List<Card> cards = new ArrayList<>();
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(cardsFindAllSql)
        ) {
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("cardname"));
                card.setDamage(rs.getInt("damage"));
                card.setCardType(CardType.valueOf(rs.getString("cardtype")));
                cards.add(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }

    @Override
    public Card findByID(int id) {

        String LookForCard = "SELECT * FROM cards WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(LookForCard)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                {
                    return new Card(rs.getString("id"),rs.getString("cardname"), rs.getInt("damage"), rs.getString("cardtype"));
                }
                else
                {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Card save(Card card) {

        // insert new package
        String InsertCard = "INSERT INTO cards(id, cardname, damage, cardtype) VALUES (?, ?, ?, ?)";

        try(PreparedStatement ps1 = conn.prepareStatement(InsertCard)) {
            ps1.setString(1, card.getId());
            ps1.setString(2, card.getName());
            ps1.setInt(3, card.getDamage());
            ps1.setString(4, card.getCardType().getName());

            ps1.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return card;
    }

    @Override
    public Card delete() {
        return null;
    }

}
