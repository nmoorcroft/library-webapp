angular.module('library.controllers')

.controller('bookListCtrl', function($scope, $location, bookService, authService) {
  $scope.books = bookService.query();
  $scope.query = '';

  $scope.hasArtwork = function(book) {
	  return book.artwork != null;
  };
  
  $scope.search = function(query) {
    $scope.books = bookService.query({
      q : query
    }, function() {
      $scope.showClear = query.length > 0;
    });
  };

  $scope.$watch('query', function() {
    $scope.showClear = false;
  });

  $scope.searchIcon = function() {
    if ($scope.showClear) {
      $scope.query = '';
    }
    $scope.search($scope.query);
  };

  $scope.canEdit = authService.isAdmin();
  
});

