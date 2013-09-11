angular.module('library.services')

.factory('bookService', function($resource) {
  return $resource('api/books/:bookId', {}, {});
});


