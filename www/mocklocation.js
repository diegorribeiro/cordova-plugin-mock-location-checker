var mocklocation = {
  check: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'MockLocationChecker', 'check', []);
  }
}

cordova.addConstructor(function () {
  if (!window.plugins) {window.plugins = {};}

  window.plugins.mocklocationchecker = mocklocation;
  return window.plugins.mocklocationchecker;
});
