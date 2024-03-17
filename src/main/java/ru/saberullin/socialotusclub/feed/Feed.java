package ru.saberullin.socialotusclub.feed;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ru.saberullin.socialotusclub.kafka.KafkaProducer;
import ru.saberullin.socialotusclub.post.Post;
import ru.saberullin.socialotusclub.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class Feed {

    private static final String FEED_PREFIX = "feed-";
    private static final String FRIEND_FEED_HEADER_NAME = "friend-feed";
    private static final int MAX_FEED_LENGTH = 1000;
    private final UserRepository userRepository;
    private final JedisPool jedisPool;
    private final KafkaProducer producer;

    public Feed(UserRepository userRepository, JedisPool jedisPool, KafkaProducer producer) {
        this.userRepository = userRepository;
        this.jedisPool = jedisPool;
        this.producer = producer;
    }

    public void pushPostToKafkaQueueFeed(Post post) {
        List<Long> userFriendsIds = userRepository.getUserFriendsIds(post.getOwnerId());
        List<Header> friendsFeedHeaders = userFriendsIds.stream()
                .map(this::createHeaderByFriendId)
                .toList();
        producer.produce(post.toJSON(), friendsFeedHeaders);
    }

    public void addPostToFeed(ConsumerRecord<String, String> postRecord) {
        try (Jedis jedis = jedisPool.getResource()) {
            String post = postRecord.value();
            postRecord.headers().headers(FRIEND_FEED_HEADER_NAME).forEach(
                    header -> {
                        String cacheFeedKey = new String(header.value(), StandardCharsets.UTF_8);
                        if (jedis.llen(cacheFeedKey) == MAX_FEED_LENGTH) {
                            jedis.lpop(cacheFeedKey);
                        }
                        jedis.lpush(cacheFeedKey, post);
                    }
            );
        }
    }

    public List<Post> getUserFeed(Long userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(FEED_PREFIX + userId, 0, -1).stream().map(Post::fromJSON).toList();
        }
    }

    private Header createHeaderByFriendId(long friendId) {
        return new RecordHeader(FRIEND_FEED_HEADER_NAME, (FEED_PREFIX + friendId).getBytes(StandardCharsets.UTF_8));
    }

}
