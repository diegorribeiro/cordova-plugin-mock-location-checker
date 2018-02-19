# cordova-plugin-mock-location-checker

This is a cordova plugin to check enabled\disabled GPS imitations in android settings.

## Supported Platforms

- Android

## Installation

Cordova local build

    # Old versions
    cordova plugin add https://github.com/diegorribeiro/cordova-plugin-mock-location-checker.git

Ionic v2 local build

    # Newest versions
    ionic cordova plugin add https://github.com/diegorribeiro/cordova-plugin-mock-location-checker.git


## Usage in javascript

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

## Usage in typescript

```ts

  (<any>window).plugins.mocklocation.check((a) => this.successCallback(a), (b) => this.errorCallback(b));


  successCallback(result) {
    console.log(result); // true - enabled, false - disabled
  }

  errorCallback(error) {
    console.log(error);
  }

```
