angular.module('library.services')

/**
 * Authentication Service for managing login and current user
 * 
 */
.factory('authService', function($http, $q) {
  var currentUser = null;
  return {
    checkLogin : function() {
      var deferred = $q.defer();
      $http.get('api/checklogin').success(function(user) {
        currentUser = user;
        deferred.resolve(currentUser);
      });
      return deferred.promise;
    },
    
    login: function(username, password) {
      var deferred = $q.defer();
      $http.post('api/authenticate', { username : username, password : password } )
      .success(function(user) {
        currentUser = user; 
        deferred.resolve(currentUser);
        
      })
      .error(function(data, status) {
        if (status == 401) {
          deferred.reject();
        }
      });
      
      return deferred.promise;

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
      var deferred = $q.defer();
      $http.post('api/logout').success(function() {
        currentUser = null; 
        deferred.resolve();
      });
      return deferred.promise;
    }
      
  };

})

.factory('checkLogin', function(authService) {
  return authService.checkLogin();
});


