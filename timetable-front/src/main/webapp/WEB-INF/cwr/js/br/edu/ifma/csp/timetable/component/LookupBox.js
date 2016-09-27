/*br.edu.ifma.csp.timetable.component.LookupBox = zk.$extends(zul.Widget, {
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
});*/

br.edu.ifma.csp.timetable.component.LookupBox = zk.$extends(zul.inp.Bandbox, {
        //super
        getPopupSize_: function (pp) {
            var bp = this.firstChild, //bandpopup
                w, h;
            if (bp) {
                w = bp._hflex == 'min' && bp._hflexsz ? jq.px0(bp._hflexsz + zk(pp).padBorderWidth()) : bp.getWidth();
                h = bp._vflex == 'min' && bp._vflexsz ? jq.px0(bp._vflexsz + zk(pp).padBorderHeight()) : bp.getHeight();
            }
            return [w||'auto', h||'auto'];
        },
        getCaveNode: function () {
            return this.$n('pp') || this.$n();
        },
        redrawpp_: function (out) {
            out.push('<div id="', this.uuid, '-pp" class="', this.$s('popup'),
            '" style="display:none">');
    
            for (var w = this.firstChild; w; w = w.nextSibling)
                w.redraw(out);
        
            out.push('</div>');
        },
        //@Override
        getIconClass_: function () {
            return 'z-icon-search';
        },
        open: function (opts) {
            if (!this.firstChild) { 
                // ignore when <bandpopup> is absent, but event is still fired
                if (opts && opts.sendOnOpen)
                    this.fire('onOpen', {open:true, value: this.getInputNode().value}, {rtags: {onOpen: 1}});
                return;
            }
            this.$supers('open', arguments);
        },
        presize_: function () {
            var bp = this.firstChild;
            if (bp && (bp._hflex == 'min' || bp._vflex == 'min')) {
                zWatch.fireDown('onFitSize', bp, {reverse: true});  
                return true;
            }
        },
        enterPressed_: function (evt) {
            //bug 3280506: do not close when children press enter.
            if(evt.domTarget == this.getInputNode())
                this.$supers('enterPressed_', arguments);
        },
        doKeyUp_: function(evt) {
            //bug 3287082: do not fire onChanging when children typing.
            if(evt.domTarget == this.getInputNode())
               this.$supers('doKeyUp_', arguments);
        },
        _fixsz: function (ppofs) {
            this.$supers('_fixsz', arguments);
            var pp = this.getPopupNode_(),
                zkpp = zk(pp),
                ppfc = pp.firstChild;
            if (ppofs[0].endsWith('%')) {
                ppfc.style.width = '100%';
            } else if (ppofs[0] != 'auto') {
                pp.style.width = zkpp.revisedWidth(ppfc.offsetWidth + zkpp.padBorderWidth()) +'px';
            }
        },
        getZclass: function() {
    		
    		var zcls = this._zclass;
    		return zcls ? zcls : 'z-lookupbox';
    	}
    });