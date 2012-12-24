Ext.util.JSONFix = new (function() {
	var useHasOwn = !!{}.hasOwnProperty, isNative = function() {
		var useNative = null;

		return function() {
			if (useNative === null) {
				useNative = Ext.USE_NATIVE_JSON && window.JSON
						&& JSON.toString() == '[object JSON]';
			}

			return useNative;
		};
	}(), pad = function(n) {
		return n < 10 ? "0" + n : n;
	}, doDecode = function(json) {
		return eval("(" + json + ")");
	}, doEncode = function(o) {
		if (!Ext.isDefined(o) || o === null) {
			return "null";
		} else if (Ext.isArray(o)) {
			return encodeArray(o);
		} else if (Ext.isDate(o)) {
			return encodeDate(o);
		} else if (Ext.isString(o)) {
			return encodeString(o);
		} else if (typeof o == "number") {

			return isFinite(o) ? String(o) : "null";
		} else if (Ext.isBoolean(o)) {
			return String(o);
		} else {
			var a = ["{"], b, i, v;
			for (i in o) {

				if (!o.getElementsByTagName) {
					if (!useHasOwn || o.hasOwnProperty(i)) {
						v = o[i];
						switch (typeof v) {
							case "undefined" :
							case "function" :
							case "unknown" :
								break;
							default :
								if (b) {
									a.push(',');
								}
								a.push(doEncode(i), ":", v === null
												? "null"
												: doEncode(v));
								b = true;
						}
					}
				}
			}
			a.push("}");
			return a.join("");
		}
	}, m = {
		"\b" : '\\b',
		"\t" : '\\t',
		"\n" : '\\n',
		"\f" : '\\f',
		"\r" : '\\r',
		'"' : '\\"',
		"\\" : '\\\\'
	}, encodeString = function(s) {
		if (/["\\\x00-\x1f]/.test(s)) {
			return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
						var c = m[b];
						if (c) {
							return c;
						}
						c = b.charCodeAt();
						return "\\u00" + Math.floor(c / 16).toString(16)
								+ (c % 16).toString(16);
					}) + '"';
		}
		return '"' + s + '"';
	}, encodeArray = function(o) {
		var a = ["["], b, i, l = o.length, v;
		for (i = 0; i < l; i += 1) {
			v = o[i];
			switch (typeof v) {
				case "undefined" :
				case "function" :
				case "unknown" :
					break;
				default :
					if (b) {
						a.push(',');
					}
					a.push(v === null ? "null" : Ext.util.JSON.encode(v));
					b = true;
			}
		}
		a.push("]");
		return a.join("");
	};

	encodeDate = function(o) {
		return '"' + o.getUTCFullYear() + "-" + pad(o.getUTCMonth() + 1) + "-"
				+ pad(o.getUTCDate()) + "T" + pad(o.getUTCHours()) + ":"
				+ pad(o.getUTCMinutes()) + ":" + pad(o.getUTCSeconds()) + '"';
	};
	this.encodeDate = encodeDate;

	this.encode = function() {
		var ec;
		return function(o) {
			if (!ec) {

				ec = isNative() ? JSON.stringify : doEncode;
			}
			return ec(o);
		};
	}();

	this.decode = function() {
		var dc;
		return function(json) {
			if (!dc) {

				dc = isNative() ? JSON.parse : doDecode;
			}
			return dc(json);
		};
	}();

})();