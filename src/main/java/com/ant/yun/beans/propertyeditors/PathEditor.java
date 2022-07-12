package com.ant.yun.beans.propertyeditors;

import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceEditor;
import com.ant.yun.util.Assert;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:55
 */
public class PathEditor extends PropertyEditorSupport {
    private final ResourceEditor resourceEditor;

    public PathEditor() {
        this.resourceEditor = new ResourceEditor();
    }

    public PathEditor(ResourceEditor resourceEditor) {
        Assert.notNull(resourceEditor, "ResourceEditor must not be null");
        this.resourceEditor = resourceEditor;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        boolean nioPathCandidate = !text.startsWith("classpath:");
        if (nioPathCandidate && !text.startsWith("/")) {
            try {
                URI uri = new URI(text);
                if (uri.getScheme() != null) {
                    nioPathCandidate = false;
                    this.setValue(Paths.get(uri).normalize());
                    return;
                }
            } catch (FileSystemNotFoundException | URISyntaxException var6) {
            }
        }

        this.resourceEditor.setAsText(text);
        Resource resource = (Resource)this.resourceEditor.getValue();
        if (resource == null) {
            this.setValue((Object)null);
        } else if (!resource.exists() && nioPathCandidate) {
            this.setValue(Paths.get(text).normalize());
        } else {
            try {
                this.setValue(resource.getFile().toPath());
            } catch (IOException var5) {
                throw new IllegalArgumentException("Failed to retrieve file for " + resource, var5);
            }
        }

    }

    public String getAsText() {
        Path value = (Path)this.getValue();
        return value != null ? value.toString() : "";
    }
}

