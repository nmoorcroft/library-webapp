package com.zuhlke.library.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.zuhlke.library.domain.User;
import com.zuhlke.library.domain.UserBuilder;
import com.zuhlke.library.repositories.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock UserRepository mockRepository;
    @InjectMocks UserService userService = new UserService();
    
    @Test
    public void shouldFindByEmail() throws Exception {
        userService.findByEmail("nmo@zuhlke.com");
        verify(mockRepository).findByEmail("nmo@zuhlke.com");
    }
    
    @Test
    public void shouldCreateUser() throws Exception {
        when(mockRepository.findByEmail("mav@zuhlke.com")).thenReturn(null);
        User user = new UserBuilder().name("Marvin").build();
        userService.createUser(user);
        verify(mockRepository).save(user);
    }
    
    @Test
    public void shouldFailWithDuplicateEmailError() throws Exception {
        User user = new UserBuilder().name("Marvin").email("mav@zuhlke.com").build();
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
