package com.ant.yun.beans;

import com.ant.yun.beans.propertyeditors.*;
import com.ant.yun.core.convert.ConversionService;
import com.ant.yun.core.io.Resource;
import com.ant.yun.lang.Nullable;
import com.ant.yun.util.ClassUtils;
import org.xml.sax.InputSource;

import java.beans.PropertyEditor;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/12 20:46
 */
public class PropertyEditorRegistrySupport implements PropertyEditorRegistry{
    @Nullable
    private ConversionService conversionService;
    private boolean defaultEditorsActive = false;
    private boolean configValueEditorsActive = false;
    @Nullable
    private Map<Class<?>, PropertyEditor> defaultEditors;
    @Nullable
    private Map<Class<?>, PropertyEditor> overriddenDefaultEditors;
    @Nullable
    private Map<Class<?>, PropertyEditor> customEditors;
    @Nullable
    private Map<String, PropertyEditorRegistrySupport.CustomEditorHolder> customEditorsForPath;
    @Nullable
    private Map<Class<?>, PropertyEditor> customEditorCache;

    public PropertyEditorRegistrySupport() {
    }

    public void setConversionService(@Nullable ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Nullable
    public ConversionService getConversionService() {
        return this.conversionService;
    }

    protected void registerDefaultEditors() {
        this.defaultEditorsActive = true;
    }

    public void useConfigValueEditors() {
        this.configValueEditorsActive = true;
    }

    public void overrideDefaultEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        if (this.overriddenDefaultEditors == null) {
            this.overriddenDefaultEditors = new HashMap();
        }

        this.overriddenDefaultEditors.put(requiredType, propertyEditor);
    }

    @Nullable
    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        if (!this.defaultEditorsActive) {
            return null;
        } else {
            if (this.overriddenDefaultEditors != null) {
                PropertyEditor editor = (PropertyEditor)this.overriddenDefaultEditors.get(requiredType);
                if (editor != null) {
                    return editor;
                }
            }

            if (this.defaultEditors == null) {
                this.createDefaultEditors();
            }

            return (PropertyEditor)this.defaultEditors.get(requiredType);
        }
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap(64);
        this.defaultEditors.put(Charset.class, new CharsetEditor());
        this.defaultEditors.put(Class.class, new ClassEditor());
        this.defaultEditors.put(Class[].class, new ClassArrayEditor());
        this.defaultEditors.put(Currency.class, new CurrencyEditor());
        this.defaultEditors.put(File.class, new FileEditor());
        this.defaultEditors.put(InputStream.class, new InputStreamEditor());
        this.defaultEditors.put(InputSource.class, new InputSourceEditor());
        this.defaultEditors.put(Locale.class, new LocaleEditor());
        this.defaultEditors.put(Path.class, new PathEditor());
        this.defaultEditors.put(Pattern.class, new PatternEditor());
        this.defaultEditors.put(Properties.class, new PropertiesEditor());
        this.defaultEditors.put(Reader.class, new ReaderEditor());
        this.defaultEditors.put(Resource[].class, new ResourceArrayPropertyEditor());
        this.defaultEditors.put(TimeZone.class, new TimeZoneEditor());
        this.defaultEditors.put(URI.class, new URIEditor());
        this.defaultEditors.put(URL.class, new URLEditor());
        this.defaultEditors.put(UUID.class, new UUIDEditor());
        this.defaultEditors.put(ZoneId.class, new ZoneIdEditor());
        this.defaultEditors.put(Collection.class, new CustomCollectionEditor(Collection.class));
        this.defaultEditors.put(Set.class, new CustomCollectionEditor(Set.class));
        this.defaultEditors.put(SortedSet.class, new CustomCollectionEditor(SortedSet.class));
        this.defaultEditors.put(List.class, new CustomCollectionEditor(List.class));
        this.defaultEditors.put(SortedMap.class, new CustomMapEditor(SortedMap.class));
        this.defaultEditors.put(byte[].class, new ByteArrayPropertyEditor());
        this.defaultEditors.put(char[].class, new CharArrayPropertyEditor());
        this.defaultEditors.put(Character.TYPE, new CharacterEditor(false));
        this.defaultEditors.put(Character.class, new CharacterEditor(true));
        this.defaultEditors.put(Boolean.TYPE, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
        this.defaultEditors.put(Byte.TYPE, new CustomNumberEditor(Byte.class, false));
        this.defaultEditors.put(Byte.class, new CustomNumberEditor(Byte.class, true));
        this.defaultEditors.put(Short.TYPE, new CustomNumberEditor(Short.class, false));
        this.defaultEditors.put(Short.class, new CustomNumberEditor(Short.class, true));
        this.defaultEditors.put(Integer.TYPE, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(Long.TYPE, new CustomNumberEditor(Long.class, false));
        this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        this.defaultEditors.put(Float.TYPE, new CustomNumberEditor(Float.class, false));
        this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        this.defaultEditors.put(Double.TYPE, new CustomNumberEditor(Double.class, false));
        this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        if (this.configValueEditorsActive) {
            StringArrayPropertyEditor sae = new StringArrayPropertyEditor();
            this.defaultEditors.put(String[].class, sae);
            this.defaultEditors.put(short[].class, sae);
            this.defaultEditors.put(int[].class, sae);
            this.defaultEditors.put(long[].class, sae);
        }

    }

    protected void copyDefaultEditorsTo(PropertyEditorRegistrySupport target) {
        target.defaultEditorsActive = this.defaultEditorsActive;
        target.configValueEditorsActive = this.configValueEditorsActive;
        target.defaultEditors = this.defaultEditors;
        target.overriddenDefaultEditors = this.overriddenDefaultEditors;
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        this.registerCustomEditor(requiredType, (String)null, propertyEditor);
    }

    public void registerCustomEditor(@Nullable Class<?> requiredType, @Nullable String propertyPath, PropertyEditor propertyEditor) {
        if (requiredType == null && propertyPath == null) {
            throw new IllegalArgumentException("Either requiredType or propertyPath is required");
        } else {
            if (propertyPath != null) {
                if (this.customEditorsForPath == null) {
                    this.customEditorsForPath = new LinkedHashMap(16);
                }

                this.customEditorsForPath.put(propertyPath, new PropertyEditorRegistrySupport.CustomEditorHolder(propertyEditor, requiredType));
            } else {
                if (this.customEditors == null) {
                    this.customEditors = new LinkedHashMap(16);
                }

                this.customEditors.put(requiredType, propertyEditor);
                this.customEditorCache = null;
            }

        }
    }

    @Nullable
    public PropertyEditor findCustomEditor(@Nullable Class<?> requiredType, @Nullable String propertyPath) {
        Class<?> requiredTypeToUse = requiredType;
        if (propertyPath != null) {
            if (this.customEditorsForPath != null) {
                PropertyEditor editor = this.getCustomEditor(propertyPath, requiredType);
                if (editor == null) {
                    List<String> strippedPaths = new ArrayList();
                    this.addStrippedPropertyPaths(strippedPaths, "", propertyPath);

                    String strippedPath;
                    for(Iterator it = strippedPaths.iterator(); it.hasNext() && editor == null; editor = this.getCustomEditor(strippedPath, requiredType)) {
                        strippedPath = (String)it.next();
                    }
                }

                if (editor != null) {
                    return editor;
                }
            }

            if (requiredType == null) {
                requiredTypeToUse = this.getPropertyType(propertyPath);
            }
        }

        return this.getCustomEditor(requiredTypeToUse);
    }

    public boolean hasCustomEditorForElement(@Nullable Class<?> elementType, @Nullable String propertyPath) {
        if (propertyPath != null && this.customEditorsForPath != null) {
            Iterator var3 = this.customEditorsForPath.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, CustomEditorHolder> entry = (Map.Entry)var3.next();
                if (PropertyAccessorUtils.matchesProperty((String)entry.getKey(), propertyPath) && ((PropertyEditorRegistrySupport.CustomEditorHolder)entry.getValue()).getPropertyEditor(elementType) != null) {
                    return true;
                }
            }
        }

        return elementType != null && this.customEditors != null && this.customEditors.containsKey(elementType);
    }

    @Nullable
    protected Class<?> getPropertyType(String propertyPath) {
        return null;
    }

    @Nullable
    private PropertyEditor getCustomEditor(String propertyName, @Nullable Class<?> requiredType) {
        PropertyEditorRegistrySupport.CustomEditorHolder holder = this.customEditorsForPath != null ? (PropertyEditorRegistrySupport.CustomEditorHolder)this.customEditorsForPath.get(propertyName) : null;
        return holder != null ? holder.getPropertyEditor(requiredType) : null;
    }

    @Nullable
    private PropertyEditor getCustomEditor(@Nullable Class<?> requiredType) {
        if (requiredType != null && this.customEditors != null) {
            PropertyEditor editor = (PropertyEditor)this.customEditors.get(requiredType);
            if (editor == null) {
                if (this.customEditorCache != null) {
                    editor = (PropertyEditor)this.customEditorCache.get(requiredType);
                }

                if (editor == null) {
                    Iterator it = this.customEditors.keySet().iterator();

                    while(it.hasNext() && editor == null) {
                        Class<?> key = (Class)it.next();
                        if (key.isAssignableFrom(requiredType)) {
                            editor = (PropertyEditor)this.customEditors.get(key);
                            if (this.customEditorCache == null) {
                                this.customEditorCache = new HashMap();
                            }

                            this.customEditorCache.put(requiredType, editor);
                        }
                    }
                }
            }

            return editor;
        } else {
            return null;
        }
    }

    @Nullable
    protected Class<?> guessPropertyTypeFromEditors(String propertyName) {
        if (this.customEditorsForPath != null) {
            PropertyEditorRegistrySupport.CustomEditorHolder editorHolder = (PropertyEditorRegistrySupport.CustomEditorHolder)this.customEditorsForPath.get(propertyName);
            if (editorHolder == null) {
                List<String> strippedPaths = new ArrayList();
                this.addStrippedPropertyPaths(strippedPaths, "", propertyName);

                String strippedName;
                for(Iterator it = strippedPaths.iterator(); it.hasNext() && editorHolder == null; editorHolder = (PropertyEditorRegistrySupport.CustomEditorHolder)this.customEditorsForPath.get(strippedName)) {
                    strippedName = (String)it.next();
                }
            }

            if (editorHolder != null) {
                return editorHolder.getRegisteredType();
            }
        }

        return null;
    }

    protected void copyCustomEditorsTo(PropertyEditorRegistry target, @Nullable String nestedProperty) {
        String actualPropertyName = nestedProperty != null ? PropertyAccessorUtils.getPropertyName(nestedProperty) : null;
        if (this.customEditors != null) {
            this.customEditors.forEach(target::registerCustomEditor);
        }

        if (this.customEditorsForPath != null) {
            this.customEditorsForPath.forEach((editorPath, editorHolder) -> {
                if (nestedProperty != null) {
                    int pos = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(editorPath);
                    if (pos != -1) {
                        String editorNestedProperty = editorPath.substring(0, pos);
                        String editorNestedPath = editorPath.substring(pos + 1);
                        if (editorNestedProperty.equals(nestedProperty) || editorNestedProperty.equals(actualPropertyName)) {
                            target.registerCustomEditor(editorHolder.getRegisteredType(), editorNestedPath, editorHolder.getPropertyEditor());
                        }
                    }
                } else {
                    target.registerCustomEditor(editorHolder.getRegisteredType(), editorPath, editorHolder.getPropertyEditor());
                }

            });
        }

    }

    private void addStrippedPropertyPaths(List<String> strippedPaths, String nestedPath, String propertyPath) {
        int startIndex = propertyPath.indexOf(91);
        if (startIndex != -1) {
            int endIndex = propertyPath.indexOf(93);
            if (endIndex != -1) {
                String prefix = propertyPath.substring(0, startIndex);
                String key = propertyPath.substring(startIndex, endIndex + 1);
                String suffix = propertyPath.substring(endIndex + 1, propertyPath.length());
                strippedPaths.add(nestedPath + prefix + suffix);
                this.addStrippedPropertyPaths(strippedPaths, nestedPath + prefix, suffix);
                this.addStrippedPropertyPaths(strippedPaths, nestedPath + prefix + key, suffix);
            }
        }

    }

    private static final class CustomEditorHolder {
        private final PropertyEditor propertyEditor;
        @Nullable
        private final Class<?> registeredType;

        private CustomEditorHolder(PropertyEditor propertyEditor, @Nullable Class<?> registeredType) {
            this.propertyEditor = propertyEditor;
            this.registeredType = registeredType;
        }

        private PropertyEditor getPropertyEditor() {
            return this.propertyEditor;
        }

        @Nullable
        private Class<?> getRegisteredType() {
            return this.registeredType;
        }

        @Nullable
        private PropertyEditor getPropertyEditor(@Nullable Class<?> requiredType) {
            return this.registeredType != null && (requiredType == null || !ClassUtils.isAssignable(this.registeredType, requiredType) && !ClassUtils.isAssignable(requiredType, this.registeredType)) && (requiredType != null || Collection.class.isAssignableFrom(this.registeredType) || this.registeredType.isArray()) ? null : this.propertyEditor;
        }
    }
}
