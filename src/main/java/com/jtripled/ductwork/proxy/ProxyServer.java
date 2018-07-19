package com.jtripled.ductwork.proxy;

import net.minecraft.util.text.translation.I18n;

/**
 *
 * @author jtripled
 */
public class ProxyServer extends Proxy
{
    @Override
    public String localize(String unlocalized, Object... args)
    {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }
}
