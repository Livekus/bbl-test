package com.bbl.test.application.service;

import com.bbl.test.adapter.backend.dtos.CreateUserRequest;
import com.bbl.test.adapter.backend.dtos.UpdateUserRequest;
import com.bbl.test.domain.model.User;
import com.bbl.test.application.service.exception;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryUserService implements UserService {
    private final ConcurrentHashMap<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @PostConstruct
    void init() {
        // Seed a few sample users (adapted from jsonplaceholder.typicode.com/users)
        this.create(new CreateUserRequest("Leanne Graham", "Bret", "Sincere@april.biz", "1-770-736-8031 x56442", "hildegard.org"));
        this.create(new CreateUserRequest("Ervin Howell", "Antonette", "Shanna@melissa.tv", "010-692-6593 x09125", "anastasia.net"));
        this.create(new CreateUserRequest("Clementine Bauch", "Samantha", "Nathan@yesenia.net", "1-463-123-4447", "ramiro.info"));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values()).stream()
                .sorted(Comparator.comparing(User::id))
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public User create(CreateUserRequest req) {
        long id = seq.incrementAndGet();
        User user = new User(
                id,
                trim(req.name()),
                trim(req.username()),
                trim(req.email()),
                trim(req.phone()),
                trim(req.website())
        );
        store.put(id, user);
        return user;
    }

    @Override
    public User update(Long id, UpdateUserRequest req) {
        if (!store.containsKey(id)) throw new UserNotFoundException(id);
        User updated = new User(
                id,
                trim(req.name()),
                trim(req.username()),
                trim(req.email()),
                trim(req.phone()),
                trim(req.website())
        );
        store.put(id, updated);
        return updated;
    }

    @Override
    public void delete(Long id) {
        if (store.remove(id) == null) throw new UserNotFoundException(id);
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}