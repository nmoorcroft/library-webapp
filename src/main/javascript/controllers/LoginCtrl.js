angular.module('library.controllers')

/**
 * Controller for loging screen 
 */
.controller('loginCtrl', function($scope, $http, $location, $timeout, authService) {

    $scope.login = function(user) {
		$http.post('api/authenticate', user).success(function(user) {
			authService.login(user);
			$location.path('/books');
			
		}).error(function(data, status) {
			if (status == 401) {
				$scope.error = 'Invalid username or password.';
				user.password = undefined;
				$timeout(function() { $('#input-password').focus(); });
			}
		});
	};
	
	$timeout(function() { $('#input-username').focus(); });

});

