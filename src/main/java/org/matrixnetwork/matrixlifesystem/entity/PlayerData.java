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
    private int lifes;

    public PlayerData(String uuid, int lifes) {
        this.uuid = uuid;
        this.lifes = lifes;
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
        } catch (Exception ignored) {
            ignored.printStackTrace();
            pd = null;
        }

        return pd;
    }

    public void addLife() {
        this.lifes++;
    }

    public void removeLife() {
        this.lifes--;
    }
}
