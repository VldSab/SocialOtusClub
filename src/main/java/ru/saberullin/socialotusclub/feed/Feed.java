package ru.saberullin.socialotusclub.feed;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.saberullin.socialotusclub.post.Post;
import ru.saberullin.socialotusclub.user.repository.UserRepository;

import java.util.List;

@Service
public class Feed {

    private static final String FEED_PREFIX = "feed-";
    private static final int MAX_FEED_LENGTH = 1000;
    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    public Feed(UserRepository userRepository, JedisPool jedisPool) {
        this.userRepository = userRepository;
        this.jedisPool = jedisPool;
    }

    public void addPostToFeed(Post post) {
        try (Jedis jedis = jedisPool.getResource()) {
            List<Long> userFriendsIds = userRepository.getUserFriendsIds(post.getOwnerId());
            userFriendsIds.forEach(
                    friendId -> {
                        String key = FEED_PREFIX + friendId;
                        if (jedis.llen(key) == MAX_FEED_LENGTH) {
                            jedis.lpop(key);
                        }
                        jedis.lpush(key, post.toJSON());
                    }
            );
        }
    }

    public List<Post> getUserFeed(Long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(FEED_PREFIX + userId, 0, -1).stream().map(Post::fromJSON).toList();
        }
    }

}
