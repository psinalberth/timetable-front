br.edu.ifma.csp.timetable.component.LookupBox = zk.$extends(zul.Widget, {
	$define: {
		msg: function(v) {
			var n;
			
			if (v && (n = this.$n))
				n.innerHTML = v;
		},
		description: null
	},
	
	getZclass: function() {
		
		var zcls = this._zclass;
		return zcls ? zcls : 'z-lookupbox';
	}
});