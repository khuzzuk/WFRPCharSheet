package pl.khuzzuk.wfrpchar.db;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.db.annot.CommitTransaction;
import pl.khuzzuk.wfrpchar.db.annot.Players;
import pl.khuzzuk.wfrpchar.db.annot.QueryTransaction;
import pl.khuzzuk.wfrpchar.entities.Player;

import java.util.List;

@Component
@Players
public class DAOPlayer implements Stateful, DAOTransactional<Player, String> {
    private List<Player> players;

    @Override
    @QueryTransaction
    public List<Player> getAllItems() {
        return players;
    }

    @Override
    @QueryTransaction
    public Player getItem(String name) {
        Player player = players.stream().filter(p -> p.getName().equals(name)).findFirst().orElseGet(Player::new);
        player.setName(name);
        return player;
    }

    @Override
    @CommitTransaction
    public boolean commit(Player player, Session session) {
        boolean query = players.stream()
                .filter(p -> p.equals(player))
                .findAny().isPresent();
        if (!query) session.save(player);
        return !query;
    }

    @Override
    public boolean requireInitialization() {
        return players==null;
    }

    @Override
    public void init(Session session) {
        players = session.createQuery("from Player").list();
    }
}
