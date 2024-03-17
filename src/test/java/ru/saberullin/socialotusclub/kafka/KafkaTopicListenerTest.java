package ru.saberullin.socialotusclub.kafka;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.EnableKafka;
import redis.clients.jedis.JedisPool;
import ru.saberullin.socialotusclub.feed.Feed;
import ru.saberullin.socialotusclub.post.Post;
import ru.saberullin.socialotusclub.user.repository.UserRepository;

import java.time.Duration;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableKafka
class KafkaTopicListenerTest {

    @MockBean
    private  UserRepository userRepository;
    @Autowired
    private  JedisPool jedisPool;
    @Autowired
    private Feed feed;

    @Test
    @SneakyThrows
    void listen_should_callWritingPostToFriendsFeeds_when_newPostListened() {
        long userId = 1;
        long userFriendId = 2;
        Post testPost = Post.builder().id(1L).ownerId(userFriendId).payload("Friend's post").build();
        when(userRepository.getUserFriendsIds(userFriendId)).thenReturn(List.of(userId));

        jedisPool.getResource().flushAll();
        feed.pushPostToKafkaQueueFeed(testPost);

        await().timeout(Duration.ofSeconds(30)).untilAsserted(() -> assertThat(feed.getUserFeed(userId)).contains(testPost));
    }
}