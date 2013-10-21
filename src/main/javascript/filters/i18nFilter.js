angular.module('library.filters')

.filter('i18n', function (messages) {
  return function(key) {
    if (typeof messages[key] != 'undefined' && messages[key] != '') {
      return messages[key];
    } else {
      return key;
    }
  };
  
});



