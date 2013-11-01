angular.module('library.controllers')

.controller('signUpCtrl', function($scope, $location, userService, authService, messages) {
  
  $scope.signup = function(user) {
    if (user.password != user.confirm) {
      $scope.error = messages.password_mismatch;

    } else {
      userService.save(user,
        // success
        function(data) {
          authService.login(user.email, user.password).then(function() {
            $location.path('/books');
          });
          
        },
  
        // error
        function(response) {
          if (response.status == 409) { // Conflict
            $scope.error = messages.email_conflict;
          }
        
        });

    }

  };

  $('#input-fullname').focus();

});

