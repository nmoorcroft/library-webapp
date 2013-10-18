angular.module('library.controllers')

.controller('loginCtrl', function($scope, $location, authService, messages) {

  $scope.login = function(user) {
    authService.login(user.username, user.password).then(function() {
      $location.path('/books');

    }, function() {
      $scope.error = messages['login_error'];
      user.password = undefined;
      $('#input-password').focus();
    
    });

  };

	$('#input-username').focus();

});


