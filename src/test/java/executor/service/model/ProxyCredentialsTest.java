package executor.service.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyCredentialsTest {
    private ProxyCredentials proxyCredentials;

    @Before
    public void setUp() {
        proxyCredentials = new ProxyCredentials("test name", "test password");
    }

    @Test
    public void getUsernameTest() {
        assertEquals("test name", proxyCredentials.getUsername());
    }

    @Test
    public void setUsernameTest() {
        proxyCredentials.setUsername("some name");
        assertEquals("some name", proxyCredentials.getUsername());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("test password", proxyCredentials.getPassword());
    }

    @Test
    public void setPasswordTest() {
        proxyCredentials.setPassword("12345");
        assertEquals("12345", proxyCredentials.getPassword());
    }

    @Test
    public void equalsTest() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials();
        proxyCredentials1.setUsername("test name");
        proxyCredentials1.setPassword("test password");
        assertEquals(proxyCredentials, proxyCredentials1);
    }

    @Test
    public void hashCodeTest() {
        ProxyCredentials proxyCredentials1 = new ProxyCredentials("test name", "test password");
        assertEquals(proxyCredentials.hashCode(), proxyCredentials1.hashCode());
    }
}