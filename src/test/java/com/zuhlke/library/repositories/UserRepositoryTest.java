package com.zuhlke.library.repositories;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.zuhlke.library.domain.User;
import com.zuhlke.library.domain.UserBuilder;
import com.zuhlke.library.domain.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/data-context.xml")
public class UserRepositoryTest {

    @Inject UserRepository userRepository;
    @Inject TransactionTemplate transactionTemplate;
    
    @Test
    public void shouldFindUserByEmail() throws Exception {
        User user = transactionTemplate.execute(new TransactionCallback<User>() {
            @Override
            public User doInTransaction(TransactionStatus status) {
                return userRepository.findByEmail("nmo@zuhlke.com");
            }
        });
        
        assertNotNull(user);
        assertEquals("Neil M", user.getName());
        
    }
    
    @Test
    public void shouldNotFindUserByEmail() throws Exception {
        User user = transactionTemplate.execute(new TransactionCallback<User>() {
            @Override
            public User doInTransaction(TransactionStatus status) {
                return userRepository.findByEmail("zzz@zuhlke.com");
            }
        });

        assertNull(user);
        
    }
    
    @Test
    public void shouldSaveNewUser() throws Exception {
        final User user = new UserBuilder()
            .withEmail("xxx@zuhlke.com")
            .withName("Marvin")
            .withPassword("pwd")
            .withRole(UserRole.ADMINISTRATOR)
            .build();
        
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                userRepository.save(user);
            }
        });
        
        User found = transactionTemplate.execute(new TransactionCallback<User>() {
           @Override
            public User doInTransaction(TransactionStatus arg0) {
               return userRepository.findByEmail("xxx@zuhlke.com");
            } 
        });
        
        assertThat(found.getId(), is(user.getId()));

    }
    
}
