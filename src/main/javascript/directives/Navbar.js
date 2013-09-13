angular.module('library.directives')

.directive('navbar', function(authService, $location, $route) {
  return {
    replace : true,
    templateUrl : 'partials/navbar.html',
    link : function($scope) {
      if (authService.isLoggedIn()) {
        $scope.fullName = authService.getFullName();
        $scope.isAdmin = authService.isAdmin();
      }
      $scope.logout = function() {
        authService.logout().then(function() {
          $location.path('/books');
          $route.reload();
        });
      };
    }
  };

});

