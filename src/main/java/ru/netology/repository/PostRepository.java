package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

    private final Map<Long, Post> repository = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    public List<Post> all() {
        if (repository.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(repository.values());
    }

    public Optional<Post> getById(long id) {
        if (repository.containsKey(id)) {
            return Optional.of(repository.get(id));
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        String content = post.getContent();
        long idPost = post.getId();
        //Если от клиента приходит пост с id=0, значит, это создание нового поста.
        if (idPost == 0) {
            id.incrementAndGet();
            repository.put(id.get(), new Post(id.get(), content));
            return new Post(id.get(), content);
        }
        //Если от клиента приходит пост с id !=0, значит, это сохранение (обновление) существующего поста.
        if (repository.containsKey(idPost)) {
            Post p = repository.get(idPost);
            p.setContent(post.getContent());
            return new Post(idPost, content);
        }
        throw new NotFoundException("couldn't save post");
    }

    public void removeById(long id) {
        Post post = repository.remove(id);
        if (post == null) {
            throw new NotFoundException("The post with the " + id + " was not deleted");
        }
    }
}
