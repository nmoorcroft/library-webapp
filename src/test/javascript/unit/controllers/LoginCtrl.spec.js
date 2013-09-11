describe('LoginCtrl', function() {
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

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });

  it('should login via auth service', inject(function($location, authService) {

    spyOn(authService, 'login');
    spyOn(authService, 'createAuthHeader').andReturn('Basic xyz==');

    $controller('loginCtrl', {
      $scope : $scope
    });

    $httpBackend.expectGET('api/authenticate', undefined, function(headers) {
      return headers['Authorization'] === 'Basic xyz=='; // not working!

    }).respond(200, {
      name : 'Me'
    });

    spyOn($location, 'path');

    $scope.login({
      username : 'username',
      password : 'password'
    });

    $httpBackend.flush();

    expect(authService.createAuthHeader).toHaveBeenCalledWith('username', 'password');
    expect(authService.login).toHaveBeenCalledWith({
      name : 'Me'
    }, 'password');
    expect($location.path).toHaveBeenCalledWith('/books');

  }));

  it('should display error for invalid username/password', inject(function($location) {

    $controller('loginCtrl', {
      $scope : $scope,
    });

    $httpBackend.expectGET('api/authenticate').respond(401, undefined);

    $scope.login({
      username : 'username',
      password : 'password'
    });

    $httpBackend.flush();

    expect($scope.error).toBe('Invalid username or password.');

  }));

});
