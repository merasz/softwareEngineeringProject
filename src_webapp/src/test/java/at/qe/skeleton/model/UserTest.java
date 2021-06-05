package at.qe.skeleton.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User();
        userUnderTest.enabled = false;
    }

    @Test
    void testGetUsername() {
        User user = new User();
        user.setUsername("Martina");
        assertTrue("Martina".equals(user.getUsername()));
    }

    @Test
    void testSetUsername() {
        User user = new User();
        user.setUsername("Martina");
        assertTrue("Martina".equals(user.getUsername()));
    }

    @Test
    void testGetPassword() {
        User user = new User();
        user.setPassword("1234");
        assertFalse("1234".equals(user.getPassword()));
    }

    @Test
    void testSetPassword() {
        User user = new User();
        user.setPassword("1234");
        assertFalse("1234".equals(user.getPassword()));
    }

    @Test
    void testIsEnabled() {
        User user = new User();
        user.setEnabled(true);
        assertTrue(user.isEnabled() == true);
    }

    @Test
    void testSetEnabled() {
        User user = new User();
        user.setEnabled(true);
        assertTrue(user.isEnabled() == true);
    }

    @Test
    void testGetTeam() {
        User user = new User();
        List<Team> teams = new ArrayList<>();
        user.setTeam(teams);
        assertTrue(user.getTeam() == teams);
    }

    @Test
    void testSetTeam() {
        User user = new User();
        List<Team> teams = new ArrayList<>();
        user.setTeam(teams);
        assertTrue(user.getTeam() == teams);
    }

    @Test
    void testGetRoles() {
        User user = new User();
        Set<UserRole> userRoleSet = new Set<UserRole>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<UserRole> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(UserRole userRole) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends UserRole> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
        user.setRoles(userRoleSet);
        assertTrue(user.getRoles() == userRoleSet);
    }

    @Test
    void testSetRoles() {
        User user = new User();
        Set<UserRole> userRoleSet = new Set<UserRole>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<UserRole> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(UserRole userRole) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends UserRole> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }
        };
        user.setRoles(userRoleSet);
        assertTrue(user.getRoles() == userRoleSet);
    }

    @Test
    void testGetRaspberry() {
        User user = new User();
        Raspberry raspberry = new Raspberry();
        user.setRaspberry(raspberry);
        assertTrue(user.getRaspberry() == raspberry);
    }

    @Test
    void testSetRaspberry() {
        User user = new User();
        Raspberry raspberry = new Raspberry();
        user.setRaspberry(raspberry);
        assertTrue(user.getRaspberry() == raspberry);
    }

    @Test
    void testGetCreateUser() {
        User user = new User();
        user.setCreateUser(user);
        assertTrue(user.getCreateUser() == user);
    }

    @Test
    void testSetCreateUser() {
        User user = new User();
        user.setCreateUser(user);
        assertTrue(user.getCreateUser() == user);
    }

    @Test
    void testGetCreateDate() {
        User user = new User();
        Date date = new Date();
        user.setCreateDate(date);
        assertTrue(user.getCreateDate() == date);
    }

    @Test
    void testSetCreateDate() {
        User user = new User();
        Date date = new Date();
        user.setCreateDate(date);
        assertTrue(user.getCreateDate() == date);
    }

    @Test
    void testGetUpdateUser() {
        User user = new User();
        user.setUpdateUser(user);
        assertTrue(user.getUpdateUser() == user);
    }

    @Test
    void testSetUpdateUser() {
        User user = new User();
        user.setUpdateUser(user);
        assertTrue(user.getUpdateUser() == user);
    }

    @Test
    void testGetUpdateDate() {
        User user = new User();
        Date date = new Date();
        user.setUpdateDate(date);
        assertTrue(user.getUpdateDate() == date);
    }

    @Test
    void testSetUpdateDate() {
        User user = new User();
        Date date = new Date();
        user.setUpdateDate(date);
        assertTrue(user.getUpdateDate() == date);
    }

    @Test
    void testHashCode() {
        final int result = userUnderTest.hashCode();
        assertThat(result).isEqualTo(413);
    }

    @Test
    void testEquals() {
        final boolean result = userUnderTest.equals("obj");
        assertThat(result).isFalse();
    }

    @Test
    void testToString() {
        final String result = userUnderTest.toString();
        assertThat(result).isEqualTo("at.qe.skeleton.model.User[ id=null ]");
    }

    @Test
    void testGetId() {
        final String result = userUnderTest.getId();
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testIsNew() {
        final boolean result = userUnderTest.isNew();
        assertThat(result).isTrue();
    }
}
