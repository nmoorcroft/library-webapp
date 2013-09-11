angular.module('library.services')

.factory('userService', function($resource) {
  return $resource('api/users/:userId', {}, {});
});

