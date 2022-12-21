package org.matrixnetwork.matrixlifesystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.matrixnetwork.matrixlifesystem.database.SessionFactoryMaker;

@Entity
@NamedQueries(
        @NamedQuery(name = "PlayerData.findByUUID", query = "select pd from PlayerData pd where pd.uuid=?1")
)
public class PlayerData {
    @Id
    @Getter
    private String uuid;

    @Getter
    private int lives;

    public PlayerData(String uuid, int lives) {
        this.uuid = uuid;
        this.lives = lives;
    }

    public PlayerData() {
    }

    public static PlayerData getPlayerData(String uuid) {
        PlayerData pd;
        SessionFactory sessionFactory = SessionFactoryMaker.getFactory();

        try (Session session = sessionFactory.openSession()) {
            pd = session.createNamedQuery("PlayerData.findByUUID", PlayerData.class)
                    .setParameter(1, uuid).getSingleResultOrNull();

            if (pd == null) {
                Transaction tx = session.beginTransaction();
                pd = new PlayerData(uuid, 0);
                session.merge(pd);
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            pd = null;
        }

        return pd;
    }

    public static void advance(PlayerData playerData) {
        SessionFactory sessionFactory = SessionFactoryMaker.getFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            playerData.addLife();
            session.merge(playerData);
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeLife(PlayerData playerData) {
        SessionFactory sessionFactory = SessionFactoryMaker.getFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            playerData.removeLife();
            session.merge(playerData);
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLife() {
        this.lives++;
    }

    public void removeLife() {
        this.lives--;
    }
}
