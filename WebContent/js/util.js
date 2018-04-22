/**
 * Created by Administrator on 2018/1/6 0006.
 */
//util 方法

var Util = {
    getBrowser : function(){
        var userAgent = navigator.userAgent;
        var isOpera = userAgent.indexOf("Opera") > -1;
        if (isOpera) {
            return "Opera"
        };
        if (userAgent.indexOf("Firefox") > -1) {
            return "Firefox";
        }
        if (userAgent.indexOf("Chrome") > -1) {
            if (window.navigator.webkitPersistentStorage.toString().indexOf('DeprecatedStorageQuota') > -1) {
                return "Chrome";
            } else {
                return "360";
            }
        }
        if (userAgent.indexOf("Safari") > -1) {
            return "Safari";
        }
        if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
            return "IE";
        };
    },
    getOS: function(){
        var sUserAgent = navigator.userAgent;
        var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows") || (navigator.platform == "Win64");
        var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
        if (isMac) return "Mac";
        var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
        if (isUnix) return "Unix";
        var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
        if (isLinux) return "Linux";
        if (isWin) {
            var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
            if (isWin2K) return "Win2000";
            var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
            if (isWinXP) return "WinXP";
            var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
            if (isWin2003) return "Win2003";
            var isWin2003 = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
            if (isWin2003) return "WinVista";
            var isWin2003 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
            if (isWin2003) return "Win7";
            var isWin10 = sUserAgent.indexOf("Windows NT 10.0") > -1 || sUserAgent.indexOf("Windows 10") > -1;
            if (isWin10) return "Win10";
        }
        return "None";
    },
    get32_64: function(){
      var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows") || (navigator.platform == "Win64");
      if(isWin){
          if(navigator.platform.indexOf("64") > -1){
              return "64位";
          }else{
              return "32位";
          }
      }else{
          return '';
      }

    },
    queryString: function (val) {
        var uri = decodeURI(window.location.search, "UTF-8");
        var re = new RegExp("" + val + "=([^&?]*)", "ig");
        return ((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1))
            : null);
    },
    setSession: function (key, value) {
        sessionStorage.setItem(key, value)
    },
    getSession: function (key) {
        return sessionStorage.getItem(key);
    },
    clearSessionStorage: function () {
        sessionStorage.clear();
    },
    setLocalStorage: function(key, value){
    	localStorage.setItem(key, value);
    },
    getLocalStorage: function(key){
    	return localStorage.getItem(key);
    },
    clearLocalStorage: function(){
    	localStorage.clear();
    }
    
}