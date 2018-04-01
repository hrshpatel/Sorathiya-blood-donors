package com.app.sorathiya.blooddonors.mailutils;

import com.app.sorathiya.blooddonors.utils.CommonUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
    public SMTPAuthenticator() {

        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        if (CommonUtils.USERNAME.length() > 0 && CommonUtils.PASSWORD.length() > 0) {

            return new PasswordAuthentication(CommonUtils.USERNAME, CommonUtils.PASSWORD);
        }

        return null;
    }
}