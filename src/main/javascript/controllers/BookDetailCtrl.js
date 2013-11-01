angular.module('library.controllers')

.controller('bookDetailCtrl', function($scope, $routeParams, $location, $cookies, bookService, messages) {

  var id = $routeParams.bookId;

  if (id !== undefined) {
    $scope.book = bookService.get({ bookId : id });
  } else {
    $scope.book = {};
  }

  $scope.hasArtwork = function() {
    return $scope.book.artwork !== undefined;  
  };
  
  $scope.artworkUrl = function() {
    return 'api/artwork/' + $scope.book.artwork;
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
 
  $('#input-artwork').attr('title', messages.browse_button);
  $('#input-artwork').bootstrapFileInput();
  $('#input-artwork').fileupload({
    dataType : 'text',
    done : function(e, data) {
      $scope.$apply(function() {
        $scope.book.artwork = data.result;
      });
    },
    fail : function(e, data) {
      $scope.$apply(function() {
        $scope.book.artwork = null;
      });
    }

  }).bind('fileuploadsend', function (e, data) {
    $scope.$apply(function() {
      data.headers['X-XSRF-TOKEN'] = $cookies['XSRF-TOKEN'];
    });
  
  }); 
  
  $('#input-title').focus();
  
});


