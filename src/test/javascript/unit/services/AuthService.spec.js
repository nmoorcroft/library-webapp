describe('AuthService', function() {

  var $rootScope = null;
  var $httpBackend = null;
  
  beforeEach(module('library.services'));

  beforeEach(inject(function(_$rootScope_, _$httpBackend_) {
    $rootScope = _$rootScope_;
    $httpBackend = _$httpBackend_;
  }));

  afterEach(function() {
    $httpBackend.verifyNoOutstandingExpectation();
    $httpBackend.verifyNoOutstandingRequest();
  });
  
  it('should not be logged in', inject(function(authService) {
    expect(authService.isLoggedIn()).toBe(false);

  }));
  
  it('should check login', inject(function(authService) {
    $httpBackend.expectGET('api/login').respond({ name : 'Me' });
    
    var currentUser = null;
    authService.checkLogin().then(function(user) {
      currentUser = user;
    });
    
    $httpBackend.flush();

    expect(currentUser).not.toBeNull();
    expect(currentUser.name).toBe('Me');
    
  }));

  it('should be logged in', inject(function(authService) {
    $httpBackend.expectPOST('api/authenticate').respond({ name : 'Me' });
 
    var currentUser = null;
    authService.login('nmo@zuhlke.com', 'password').then(function(user) {
      currentUser = user;
    });
    
    $httpBackend.flush();
    
    expect(authService.isLoggedIn()).toBe(true);
    expect(authService.getFullName()).toBe('Me');

    expect(currentUser).not.toBeNull();
    expect(currentUser.name).toBe('Me');
    
  }));

  it('should be logged out', inject(function(authService, $http) {
    $httpBackend.expectPOST('api/logout').respond();

    authService.logout();
    
    $httpBackend.flush();
    
    expect(authService.isLoggedIn()).toBe(false);

  }));

  it('should be admin', inject(function(authService) {
    $httpBackend.expectPOST('api/authenticate').respond({ role : 'ADMINISTRATOR' });
    
    authService.login('nmo@zuhlke.com', 'password');
    
    $httpBackend.flush();
    
    expect(authService.isLoggedIn()).toBe(true);

    expect(authService.isAdmin()).toBe(true);

  }));

  it('should not be admin', inject(function(authService) {
    $httpBackend.expectPOST('api/authenticate').respond({ role : 'USER' });
    
    authService.login('nmo@zuhlke.com', 'password');
    
    $httpBackend.flush();
    
    expect(authService.isLoggedIn()).toBe(true);
  
    expect(authService.isAdmin()).toBe(false);
  
  }));


});
