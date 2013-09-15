describe('AuthService specification', function() {

  var $httpBackend = null;
  
  beforeEach(module('library.services'));

  beforeEach(inject(function($_$httpBackend_) {
    $httpBackend = _$httpBackend_;
  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });
  
  it('should not be logged in', inject(function(authService) {
    expect(authService.isLoggedIn()).toBe(false);

  }));

  it('should be logged in', inject(function(authService) {
    var user = { };
    $httpBackend.expectPOST('api/authenticate').thenReturn(user);
    authService.login('nmo@zuhlke.com', password);
    expect(authService.isLoggedIn()).toBe(true);
    expect(authService.getFullName()).toBe('Me');

  }));

  it('should be logged out', inject(function(authService, $http) {
    authService.login({
      name : 'Me'
    }, 'password');
    expect(authService.isLoggedIn()).toBe(true);

    authService.logout();
    expect(authService.isLoggedIn()).toBe(false);
    expect($http.defaults.headers.common['Authorization']).toBeUndefined();

  }));

  it('should be admin', inject(function(authService) {
    authService.login({
      name : 'Me',
      role : 'ADMINISTRATOR'
    }, 'password');
    expect(authService.isAdmin()).toBe(true);

  }));

  it('should not be admin', inject(function(authService) {
    authService.login({
      name : 'Me',
      role : 'USER'
    }, 'password');
    expect(authService.isAdmin()).toBe(false);

  }));

});
