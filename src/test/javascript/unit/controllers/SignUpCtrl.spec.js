describe('SignUpCtrl', function() {
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

  it('should save new user', inject(function($q, $location, authService) {

    var user = { email : 'email', password : 'password', confirm : 'password' };
    
    var deferred = $q.defer();
    deferred.resolve();
    spyOn(authService, 'login').andReturn(deferred.promise);
    
    $httpBackend.expectPOST('api/users', user).respond(200);

    spyOn($location, 'path');

    $controller('signUpCtrl', { $scope : $scope });

    $scope.signup(user);

    $httpBackend.flush();

    expect($location.path).toHaveBeenCalledWith('/books');
    expect(authService.login).toHaveBeenCalledWith('email', 'password');
    

  }));

  it('should error for duplicate email', inject(function(messages) {
    
    messages.email_conflict = 'This email address has already been registered, please use another.';
    
    $controller('signUpCtrl', {
      $scope : $scope,
    });
    
    $httpBackend.expectPOST('api/users', { email : 'email' }).respond(409);
    
    $scope.signup({ email : 'email' });
    
    $httpBackend.flush();
    
    expect($scope.error).toBe('This email address has already been registered, please use another.');
    
  }));
  
  it('should fail when passwords do not match', inject(function(messages) {

    messages.password_mismatch = 'Passwords must match';
    
    $controller('signUpCtrl', { $scope : $scope });
    
    $scope.signup({ email : 'email', password : 'p1', confirm : 'p2' });
    
    expect($scope.error).toBe('Passwords must match');
    
    
  }));
  
});
