angular.module('library.controllers')

/**
 * Controller for loging screen 
 */
.controller('loginCtrl', function($scope, $http, $location, $timeout, authService) {

  $scope.login = function(user) {
    authService.login(user.username, user.password).then(function() {
      $location.path('/books');

    }, function() {
      $scope.error = 'Invalid username or password.';
      user.password = undefined;
      $('#input-password').focus();
    
    });

  };

	$('#input-username').focus();

});


