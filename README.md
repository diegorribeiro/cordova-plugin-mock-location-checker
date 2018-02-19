# cordova-plugin-mock-location-checker

This is a cordova plugin to check enabled\disabled GPS imitations in android settings.

## Supported Platforms

- Android

## Installation

Cordova local build

    cordova plugin add https://github.com/diegorribeiro/cordova-plugin-mock-location-checker.git

## Usage

```js
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
  window.plugins.mocklocationchecker.check(successCallback, errorCallback);
}

function successCallback(result) {
  console.log(result); // true - enabled, false - disabled
}

function errorCallback(error) {
  console.log(error);
}
```
