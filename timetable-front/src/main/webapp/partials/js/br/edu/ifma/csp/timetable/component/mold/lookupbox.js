function s(out) {
	
	var msg = this._msg;
	
	out.push('<span id="', this.uuid, '"class="', this.getZclass(), '">');
	
	if (msg)
		out.push(msg);
	out.push('</span>');
}