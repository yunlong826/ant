package com.ant.yun.beans.propertyeditors;

import com.ant.yun.core.io.Resource;
import com.ant.yun.core.io.ResourceEditor;
import com.ant.yun.util.Assert;
import com.ant.yun.util.ResourceUtils;
import com.ant.yun.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:50
 */
public class FileEditor extends PropertyEditorSupport {
    private final ResourceEditor resourceEditor;

    public FileEditor() {
        this.resourceEditor = new ResourceEditor();
    }

    public FileEditor(ResourceEditor resourceEditor) {
        Assert.notNull(resourceEditor, "ResourceEditor must not be null");
        this.resourceEditor = resourceEditor;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            this.setValue((Object)null);
        } else {
            File file = null;
            if (!ResourceUtils.isUrl(text)) {
                file = new File(text);
                if (file.isAbsolute()) {
                    this.setValue(file);
                    return;
                }
            }

            this.resourceEditor.setAsText(text);
            Resource resource = (Resource)this.resourceEditor.getValue();
            if (file != null && !resource.exists()) {
                this.setValue(file);
            } else {
                try {
                    this.setValue(resource.getFile());
                } catch (IOException var5) {
                    throw new IllegalArgumentException("Could not retrieve file for " + resource + ": " + var5.getMessage());
                }
            }

        }
    }

    public String getAsText() {
        File value = (File)this.getValue();
        return value != null ? value.getPath() : "";
    }
}
