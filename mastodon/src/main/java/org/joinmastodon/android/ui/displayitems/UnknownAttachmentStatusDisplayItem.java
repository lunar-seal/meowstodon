package org.joinmastodon.android.ui.displayitems;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joinmastodon.android.R;
import org.joinmastodon.android.model.Attachment;
import org.joinmastodon.android.ui.utils.UiUtils;

import me.grishka.appkit.utils.V;

public class UnknownAttachmentStatusDisplayItem extends StatusDisplayItem{
	public final Attachment attachment;
	public final String accountID;
	public final Object parentObject;

	public UnknownAttachmentStatusDisplayItem(String parentID, Callbacks callbacks, Context context, Attachment attachment, String accountID, Object parentObject){
		super(parentID, callbacks, context);
		this.attachment=attachment;
		this.accountID=accountID;
		this.parentObject=parentObject;
	}

	@Override
	public Type getType(){
		return Type.UNKNOWN_ATTACHMENT;
	}

	public static class Holder extends StatusDisplayItem.Holder<UnknownAttachmentStatusDisplayItem>{
		private final TextView text;

		public Holder(Context context, ViewGroup parent){
			super(context, R.layout.item_text_with_icon, parent);
			text=(TextView)itemView;
			text.setOnClickListener(v->{
				UiUtils.openURL(v.getContext(), item.accountID, item.attachment.url, item.parentObject);
			});
		}

		@Override
		public void onBind(UnknownAttachmentStatusDisplayItem item){
			text.setPaddingRelative(V.dp(item.fullWidth ? 16 : 64), 0, V.dp(16), 0);
			text.setText(item.attachment.url);
			text.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_link_24px, 0, 0, 0);
		}
	}
}
