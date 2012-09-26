package org.modica.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.modica.web.lazy.LazyViewPanel;
import org.modica.web.treeview.TreeViewPanel;

public class MainPage extends WebPage {

    private static final long serialVersionUID = 1L;

    private static final String CONTENT_ID = "content";

    private final List<MenuItem> menuItems = Arrays.asList(
            new MenuItem("Tree View", new TreeViewPanel(CONTENT_ID)),
            new MenuItem("Flat View", new LazyViewPanel(CONTENT_ID))/*,
            new MenuItem("settings", new SettingsPanel(CONTENT_ID))*/);

    private Panel activePanel;

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference("css/reset.css");
        response.renderCSSReference(new PackageResourceReference(MainPage.class, "MainPage.css"));
    }

    public MainPage() {
        activePanel = menuItems.get(0).getPanel();
        add(activePanel);
        initMenu();
    }

    private void initMenu() {
        add(new ListView<MenuItem>("tab", menuItems) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<MenuItem> item) {
                final MenuItem menuItem = item.getModelObject();
                final Panel panel = menuItem.getPanel();
                if (panel.equals(activePanel)) {
                    item.add(new AttributeModifier("class", new Model<String>("active")));
                }
                Link<Void> link = new Link<Void>("tab_link") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick() {
                        activePanel.replaceWith(panel);
                        activePanel = panel;
                    }
                };
                item.add(link);
                link.add(new Label("caption", menuItem.getCaption()));
            }
        });
    }

    static class MenuItem implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String caption;

        private final Panel panel;

        public MenuItem(String caption, Panel panel) {
            this.caption = caption;
            this.panel = panel;
        }

        public String getCaption() {
            return caption;
        }

        public Panel getPanel() {
            return panel;
        }
    }
}
