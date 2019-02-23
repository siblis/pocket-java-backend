package ru.geekbrains.pocket.backend.domain.db;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user1;
    private User user2;
    private User user3;
    @Before
    public void setUp() throws Exception{
    user1 = new User("user1@test.com", "111111","serg");
    user2 = new User("user2@test.com", "111111", "victor");
    user3 = new User("user2@test.com", "1111111","alex");
    }

    @Test
    public void toString() {

    }

    @Test
    public void setId() {
    }

    @Test
    public void setEmail() {
    }

    @Test
    public void setPassword() {
    }

    @Test
    public void setProfile() {
    }

    @Test
    public void setRoles() {
    }

    @Test
    public void setCreatedAt() {
    }

    @Test
    public void setEnabled() {
    }

    @Test
    public void setUsing2FA() {
    }

    @Test
    public void setSecret() {
    }

    @Test
    public void getId() {
    }

    @Test
    public void getEmail() {
    }

    @Test
    public void getPassword() {
    }

    @Test
    public void getProfile() {
    }

    @Test
    public void getRoles() {
    }

    @Test
    public void getCreatedAt() {
    }

    @Test
    public void isEnabled() {
    }

    @Test
    public void isUsing2FA() {
    }

    @Test
    public void getSecret() {
    }
}
