package org.afpparser.web;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.afpparser.web.MainPage.MenuItem;
import org.afpparser.web.extension.Extension;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.google.common.base.Predicate;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see org.afpparser.web.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

    private List<Extension> extensions;

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<MainPage> getHomePage() {
        return MainPage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        try {
            findExtensions();
        } catch (Exception e) {
            throw new WicketRuntimeException("A problem occurred finding plugins");
        }
    }

    public List<MenuItem> getExtensionMenuItems() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        for (Extension ext : extensions) {
            menuItems.add(ext.createMainMenuItem(MainPage.CONTENT_ID));
        }
        return menuItems;
    }


    private void findExtensions() throws MalformedURLException, InstantiationException, IllegalAccessException {
        Set<URL> classpath = new HashSet<URL>();

        String pluginsClassPath = System.getProperty("plugins.classpath", "");
        String[] pluginArray = pluginsClassPath.split(":");
        for (String plugin : pluginArray) {
            classpath.add(new File(plugin).toURI().toURL());
        }

        //TODO not very refactor safe - can we do better?
        Predicate<String> filter = new FilterBuilder().include("org.afpparser.*");

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .filterInputsBy(filter)
                .setUrls(classpath)
                .setScanners(new SubTypesScanner()));

        Set<Class<? extends Extension>> extensionClasses = reflections.getSubTypesOf(Extension.class);

        extensions = new ArrayList<Extension>();

        for(Class extensionClass : extensionClasses) {
            Extension extension = (Extension) extensionClass.newInstance();
            //System.out.println("Found plugin: " + extension);
            extensions.add(extension);
        }
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

}
