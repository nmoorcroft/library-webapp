//describe('SignUpCtrl', function() {
//  var $scope = null;
//  var $httpBackend = null;
//  var $controller = null;
//
//  beforeEach(module('library.controllers'));
//  beforeEach(module('library.services'));
//
//  beforeEach(inject(function($rootScope, _$controller_, _$httpBackend_) {
//    $scope = $rootScope.$new();
//    $httpBackend = _$httpBackend_;
//    $controller = _$controller_;
//  }));
//
//  afterEach(function() {
//    $httpBackend.verifyNoOutstandingExpectation();
//    $httpBackend.verifyNoOutstandingRequest();
//  });
//
//  it('should save new user', inject(function($location, authService) {
//
//    var user = { email : 'email', password : 'password', confirm : 'password' };
//    
//    spyOn(authService, 'login');
//    
//    $controller('signUpCtrl', {
//      $scope : $scope
//    });
//
//    $httpBackend.expectPOST('api/users', user).respond(200);
//
//    spyOn($location, 'path');
//
//    $scope.signup(user);
//
//    $httpBackend.flush();
//
//    expect($location.path).toHaveBeenCalledWith('/books');
//    //expect(authService.login).toHaveBeenCalledWith(user, 'password');
//    
//
//  }));
//
//  it('should error for duplicate email', function() {
//    
//    $controller('signUpCtrl', {
//      $scope : $scope,
//    });
//    
//    $httpBackend.expectPOST('api/users', { email : 'email' }).respond(409);
//    
//    $scope.signup({ email : 'email', password : 'pwd', confirm : 'pwd' });
//    
//    $httpBackend.flush();
//    
//    expect($scope.error).toBe('This email address has already been registered, please use another.');
//    
//  });
//  
//  it('should fail when passwords do not match', function() {
//
//    $controller('signUpCtrl', {
//      $scope : $scope
//    });
//    
//    $scope.signup({ email : 'email', password : 'p1', confirm : 'p2' });
//    
//    expect($scope.error).toBe('Passwords must match');
//    
//    
//  });
//  
//});
