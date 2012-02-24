package org.afpparser.web.extension;

import org.afpparser.web.MainPage.MenuItem;

public interface Extension {

    MenuItem createMainMenuItem(String panelId);
}
