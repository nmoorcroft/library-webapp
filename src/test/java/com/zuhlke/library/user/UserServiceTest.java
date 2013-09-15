package com.zuhlke.library.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.zuhlke.library.domain.User;
import com.zuhlke.library.domain.UserBuilder;
import com.zuhlke.library.repositories.UserRepository;
import com.zuhlke.library.security.SecurityUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock UserRepository mockRepository;
    @Mock SecurityUtils mockSecurityUtils;
    @InjectMocks UserService userService = new UserService();
    
    @Test
    public void shouldFindByEmail() throws Exception {
        userService.findByEmail("nmo@zuhlke.com");
        verify(mockRepository).findByEmail("nmo@zuhlke.com");
    }
    
    @Test
    public void shouldCreateUser() throws Exception {
        when(mockRepository.findByEmail("mav@zuhlke.com")).thenReturn(null);
        when(mockSecurityUtils.hash("password", "mav@zuhlke.com")).thenReturn("hashed");
        User user = new UserBuilder().withName("Marvin").withEmail("mav@zuhlke.com").withPassword("password").build();
        userService.createUser(user);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(mockRepository).save(captor.capture());
        Assert.assertEquals("mav@zuhlke.com", captor.getValue().getEmail());
        Assert.assertEquals("hashed", captor.getValue().getPassword());
    }
    
    @Test
    public void shouldFailWithDuplicateEmailError() throws Exception {
        User user = new UserBuilder().withName("Marvin").withEmail("mav@zuhlke.com").build();
        when(mockRepository.findByEmail("mav@zuhlke.com")).thenReturn(user);
        try {
            userService.createUser(user);
            fail();
            
        } catch (WebApplicationException e) {
            assertEquals(409, e.getResponse().getStatus());
        }
        
        verify(mockRepository, never()).save(user);
    
    }
    
    
}
