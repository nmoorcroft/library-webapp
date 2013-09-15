describe('LoginCtrl', function() {
  var $rootScope = null;
  var $scope = null;
  var $controller = null;
  var $q = null;
  
  beforeEach(module('library.controllers'));
  beforeEach(module('library.services'));

  beforeEach(inject(function(_$rootScope_, _$controller_, _$q_) {
    $rootScope = _$rootScope_;
    $scope = $rootScope.$new();
    $controller = _$controller_;
    $q = _$q_;
  }));

  it('should login via auth service', inject(function($location, authService) {

    var deferred = $q.defer();
    deferred.resolve({ name : 'Me'});
    spyOn(authService, 'login').andReturn(deferred.promise);

    $controller('loginCtrl', {
      $scope : $scope
    });

    spyOn($location, 'path');

    $scope.login({
      username : 'username',
      password : 'password'
    });

    $rootScope.$apply();

    expect(authService.login).toHaveBeenCalledWith('username', 'password');
    expect($location.path).toHaveBeenCalledWith('/books');

  }));

  it('should display error for invalid username/password', inject(function($location, authService) {

    var deferred = $q.defer();
    deferred.reject();
    spyOn(authService, 'login').andReturn(deferred.promise);
    
    $controller('loginCtrl', {
      $scope : $scope,
    });

    $scope.login({
      username : 'username',
      password : 'password'
    });

    $rootScope.$apply();
    
    expect($scope.error).toBe('Invalid username or password.');

  }));

});
