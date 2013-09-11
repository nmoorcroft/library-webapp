angular.module('library.services')

.factory('authService', function($http) {
  var currentUser = null;
  return {
    login: function(user) { 
      currentUser = user; 
    },
    
    isLoggedIn: function() { 
      return currentUser !== null; 
    },
    
    isAdmin: function() { 
      return this.isLoggedIn() && currentUser.role == 'ADMINISTRATOR'; 
    },
    
    getFullName: function() { 
      return this.isLoggedIn() ? currentUser.name : null; 
    },
    
    logout: function() { 
      currentUser = null; 
    }
      
  };

});

