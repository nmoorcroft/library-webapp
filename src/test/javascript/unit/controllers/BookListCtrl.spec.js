describe('BookListCtrl', function() {

  var books = [ {
    id : 1,
    title : "Domain-Driven Design",
    isbn : "0-321-12521-5",
    author : "Eric Evans"
  }, {
    id : 3,
    title : "Java Persistence with Hibernate",
    isbn : "1-932394-88-5",
    author : "Christian Bauer, Gavin King"
  } ];

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
    $httpBackend.when('GET', 'api/books').respond(books);
  });

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  it('should load all books when started', function() {

    var mockUserService = {
      isAdmin : function() {
        return false;
      }
    };

    $httpBackend.expectGET('api/books').respond(books);

    $controller('bookListCtrl', {
      $scope : $scope,
      userService : mockUserService
    });

    expect($scope.search).toBeDefined();
    expect($scope.searchIcon).toBeDefined();
    expect($scope.canEdit).toBe(false);

    expect($scope.books).toEqualData([]);

    $httpBackend.flush();

    expect($scope.books).toEqualData(books);

  });

  it('should search for books using query', function() {

    var mockAuthService = {
      isAdmin : function() {
        return false;
      }
    };

    $controller('bookListCtrl', {
      $scope : $scope,
      authService : mockAuthService
    });

    $httpBackend.expectGET('api/books?q=query').respond(books);

    $scope.query = 'query';
    $scope.search($scope.query);

    $httpBackend.flush();

    expect($scope.showClear).toBe(true);

  });

  it('should hide the clear button for an empty search', function() {

    $controller('bookListCtrl', {
      $scope : $scope
    });

    $httpBackend.expectGET('api/books?q=').respond(books);

    $scope.search('');

    $httpBackend.flush();

    expect($scope.showClear).toBe(false);

  });

  it('should show edit button for admin', function() {
    var mockauthService = {
      isAdmin : function() {
        return true;
      }
    };

    $httpBackend.expectGET('api/books').respond(books);

    $controller('bookListCtrl', {
      $scope : $scope,
      authService : mockauthService
    });

    $httpBackend.flush();

    expect($scope.canEdit).toBe(true);

  });

  it('should clear search when icon clear clicked', function() {

    $controller('bookListCtrl', {
      $scope : $scope
    });
    $httpBackend.flush();

    spyOn($scope, 'search');

    $scope.showClear = true;
    $scope.query = 'query';
    $scope.searchIcon();

    expect($scope.search).toHaveBeenCalledWith('');

  });

  it('should execute search when icon search clicked', function() {

    $controller('bookListCtrl', {
      $scope : $scope
    });
    
    $httpBackend.flush();

    spyOn($scope, 'search');
    $scope.showClear = false;
    $scope.query = 'query';

    $scope.searchIcon();

    expect($scope.search).toHaveBeenCalledWith('query');

  });

});
