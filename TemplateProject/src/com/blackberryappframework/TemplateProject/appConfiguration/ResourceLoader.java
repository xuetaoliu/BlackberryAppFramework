package com.blackberryappframework.TemplateProject.appConfiguration;

import Resource.TemplateProjectResource;
import net.rim.device.api.i18n.MessageFormat;
import net.rim.device.api.i18n.ResourceBundle;
import net.rim.device.api.i18n.ResourceBundleFamily;

public class ResourceLoader implements TemplateProjectResource{
    private static ResourceBundleFamily _bundle = ResourceBundle.getBundle( BUNDLE_ID, BUNDLE_NAME );

    public static String getString( int id ) {
        return _bundle.getString( id );
    }

    public static String getString(int id, String arg) {
        return getString(id, new String[]{arg});
    }

    public static String getString(int id, Object arg[]){
        String s = _bundle.getString(id);
        return MessageFormat.format(s, arg);
    }

    public static ResourceBundleFamily getFamily() {
        return _bundle;
    }

}
