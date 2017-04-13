package no.teacherspet.mainapplication;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

class ViewWrapper {
    View base;
	RatingBar rate=null;
	TextView label=null;
	FloatingActionButton comment=null;
	
	ViewWrapper(View base) {
		this.base=base;
	}
	
	RatingBar getRatingBar() {
		if (rate==null) {
			rate=(RatingBar)base.findViewById(R.id.rate);
		}
		return(rate);
	}
	
	TextView getLabel() {
		if (label==null) {
			label=(TextView)base.findViewById(R.id.label);
		}
		return(label);
	}

	FloatingActionButton getCommentFAB(){
		if (comment==null){
			comment=(FloatingActionButton) base.findViewById(R.id.showCommentBtn);
		}
		return (comment);
	}
	
}