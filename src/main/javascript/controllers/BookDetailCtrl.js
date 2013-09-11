angular.module('library.controllers')

.controller('bookDetailCtrl', function($scope, $routeParams, $location, bookService) {
  var id = $routeParams.bookId;
  if (!_.isUndefined(id)) {
    $scope.book = bookService.get({ bookId : id });
  } else {
    $scope.book = {};
  }

  $scope.hasArtwork = function() {
	return $scope.book.artwork != null;  
  };
  
  $scope.artworkUrl = function() {
    if (!_.isUndefined($scope.book.artwork)) {
      return 'api/artwork/' + $scope.book.artwork;
    }
    return '';
  };

  $scope.save = function(book) {
    bookService.save(book, function() {
      $location.path('/books');
    });
  };

  $scope.remove = function(id) {
    bookService.remove({ bookId : id }, function() {
      $location.path('/books');
    });
  };

  $('#input-artwork').bootstrapFileInput();
  $('#input-artwork').fileupload({
    dataType : 'text',
    done : function(e, data) {
      $scope.$apply(function() {
        $scope.book.artwork = data.result;
      });
    }
  });

  $('#input-title').focus();

});


