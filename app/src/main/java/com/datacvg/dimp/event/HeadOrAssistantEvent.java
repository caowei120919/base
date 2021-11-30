package com.datacvg.dimp.event;

import androidx.annotation.Keep;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.Contact;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-04
 * @Description :
 */
@Keep
public class HeadOrAssistantEvent {
    private boolean checked = false ;
    private Contact contact ;
    private int chooseType = Constants.CHOOSE_TYPE_HEAD ;

    public HeadOrAssistantEvent(Contact contact, boolean b, int chooseType) {
        this.checked = b ;
        this.chooseType = chooseType ;
        this.contact = contact ;
    }

    public boolean isChecked() {
        return checked;
    }

    public Contact getContact() {
        return contact;
    }

    public int getChooseType() {
        return chooseType;
    }
}
