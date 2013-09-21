angular.module('library.controllers')

.controller('loginCtrl', function($scope, $location, authService) {

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


