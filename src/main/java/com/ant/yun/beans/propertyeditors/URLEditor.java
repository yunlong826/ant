package com.ant.yun.beans.propertyeditors;

import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceEditor;
import com.ant.yun.util.Assert;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URL;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 21:00
 */
public class URLEditor extends PropertyEditorSupport {
    private final ResourceEditor resourceEditor;

    public URLEditor() {
        this.resourceEditor = new ResourceEditor();
    }

    public URLEditor(ResourceEditor resourceEditor) {
        Assert.notNull(resourceEditor, "ResourceEditor must not be null");
        this.resourceEditor = resourceEditor;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.resourceEditor.setAsText(text);
        Resource resource = (Resource)this.resourceEditor.getValue();

        try {
            this.setValue(resource != null ? resource.getURL() : null);
        } catch (IOException var4) {
            throw new IllegalArgumentException("Could not retrieve URL for " + resource + ": " + var4.getMessage());
        }
    }

    public String getAsText() {
        URL value = (URL)this.getValue();
        return value != null ? value.toExternalForm() : "";
    }
}
