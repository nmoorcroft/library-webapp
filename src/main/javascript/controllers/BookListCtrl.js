angular.module('library.controllers')

.controller('bookListCtrl', function($scope, bookService, authService) {
  
  $scope.books = bookService.query();
  $scope.query = '';

  $scope.search = function(query) {
    $scope.books = bookService.query({ q : query }, function() {
      $scope.showClear = query.length > 0;
    });
  };

  $scope.searchIcon = function() {
    if ($scope.showClear) {
      $scope.query = '';
    }
    $scope.search($scope.query);
  };

  $scope.canEdit = authService.isAdmin();

  $scope.hasArtwork = function(book) {
    return book.artwork != null;
  };
  
});


