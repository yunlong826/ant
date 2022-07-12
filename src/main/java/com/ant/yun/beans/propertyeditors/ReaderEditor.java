package com.ant.yun.beans.propertyeditors;

import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceEditor;
import com.ant.yun.core.io.support.EncodedResource;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.Assert;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:57
 */
public class ReaderEditor extends PropertyEditorSupport {
    private final ResourceEditor resourceEditor;

    public ReaderEditor() {
        this.resourceEditor = new ResourceEditor();
    }

    public ReaderEditor(ResourceEditor resourceEditor) {
        Assert.notNull(resourceEditor, "ResourceEditor must not be null");
        this.resourceEditor = resourceEditor;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        this.resourceEditor.setAsText(text);
        Resource resource = (Resource)this.resourceEditor.getValue();

        try {
            this.setValue(resource != null ? (new EncodedResource(resource)).getReader() : null);
        } catch (IOException var4) {
            throw new IllegalArgumentException("Failed to retrieve Reader for " + resource, var4);
        }
    }

    @Nullable
    public String getAsText() {
        return null;
    }
}

