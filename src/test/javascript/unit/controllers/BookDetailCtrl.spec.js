describe('BookDetailCtrl', function() {
  var $scope = null;
  var $httpBackend = null;
  var $controller = null;

  beforeEach(module('library.controllers'));
  beforeEach(module('library.services'));

  beforeEach(inject(function($rootScope, _$controller_, _$httpBackend_) {
    $scope = $rootScope.$new();
    $httpBackend = _$httpBackend_;
    $controller = _$controller_;
  }));

  beforeEach(function() {
    $httpBackend.when('GET', 'api/books/1').respond({
      id : 1,
      title : "Domain-Driven Design",
      isbn : "0-321-12521-5",
      author : "Eric Evans"
    });
  });

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  it('should configure fileupload control', function() {
    var bootstrapFileInput = spyOn($.fn, 'bootstrapFileInput');
    var uploadParams = null;
    var mockFileUpload = { bind : function() {} };
    var fileupload = spyOn($.fn, 'fileupload').andCallFake(function(params) {
      uploadParams = params;
      return mockFileUpload;
    });
    
    spyOn(mockFileUpload, 'bind');

    $controller('bookDetailCtrl', {
      $scope : $scope,
      $routeParams : { bookId : 1 }
    });

    $httpBackend.flush();

    expect(bootstrapFileInput.mostRecentCall.object.selector).toEqual('#input-artwork');
    expect(bootstrapFileInput).toHaveBeenCalled();
    expect(mockFileUpload.bind).toHaveBeenCalled();

    expect(fileupload).toHaveBeenCalled();

    // test the callback
    uploadParams.done('', { result : 'xxx' });

    expect($scope.book.artwork).toEqual('xxx');

  });

  it('should load a book by id', inject(function($location) {

    $httpBackend.expectGET('api/books/1').respond({ artwork : '123' });

    $controller('bookDetailCtrl', {
      $scope : $scope,
      $routeParams : { bookId : 1 }
    });

    $httpBackend.flush();

    expect($scope.save).toBeDefined();
    expect($scope.remove).toBeDefined();
    expect($scope.artworkUrl).toBeDefined();
    expect($scope.artworkUrl()).toBe('api/artwork/123');

  }));

  it('should load an empty book', inject(function($location) {

    $controller('bookDetailCtrl', {
      $scope : $scope
    });

    expect($scope.save).toBeDefined();
    expect($scope.remove).toBeDefined();
    expect($scope.book.id).not.toBeDefined();

  }));

  it('should load a book without artwork', inject(function($location) {

    $httpBackend.expectGET('api/books/1').respond({});

    $controller('bookDetailCtrl', {
      $scope : $scope,
      $routeParams : { bookId : 1 }
    });

    $httpBackend.flush();

    expect($scope.hasArtwork()).toBe(false);

  }));

  it('should save a book', inject(function($location) {

    spyOn($location, 'path');

    $httpBackend.expectPOST('api/books').respond({});

    $controller('bookDetailCtrl', {
      $scope : $scope,
      $routeParams : {
        bookId : 1
      }
    });

    $scope.save({
      id : 1,
      title : "Domain-Driven Design - modified",
      isbn : "0-321-12521-5",
      author : "Eric Evans"
    });

    $httpBackend.flush();

    expect($location.path).toHaveBeenCalledWith('/books');

  }));

  it('should remove a book', inject(function($location) {

    spyOn($location, 'path');

    $httpBackend.expectDELETE('api/books/1').respond({});

    $controller('bookDetailCtrl', {
      $scope : $scope,
      $routeParams : {
        bookId : 1
      }
    });

    $scope.remove(1);

    $httpBackend.flush();

    expect($location.path).toHaveBeenCalledWith('/books');

  }));

});
