package com.chen.java8.example.httpUtils;

import com.suneee.scn.config.SystemTools;

import java.io.*;
import java.util.*;

/**
 * FileName: PropertiesFactory
 * Author:   SunEee
 * Date:     2018/2/27 10:10
 * Description: 读取和封装properties文件的属性
 */
public class PropertiesFactory {
    private PropertiesFactory() {
    }

    public static Properties createProperties(String projectName, String propertiesName) {
        Properties properties = new Properties();
        String path = projectName + "/" + propertiesName;
        ResourceBundle rb = ResourceBundle.getBundle(path.trim());
        Enumeration<String> allKey = rb.getKeys();
        List ps = getParent(projectName, propertiesName);

        while(true) {
            while(allKey.hasMoreElements()) {
                String key = ((String)allKey.nextElement()).trim();
                String value = rb.getString(key).trim();
                if (value == null || value.length() == 0 || value.contains("$parent$")) {
                    String pValue = getParentValue(ps, key, value);
                    if (pValue != null) {
                        value = pValue;
                    }
                }

                if ("jdbc.maxActive".equals(key) && SystemTools.isWindows()) {
                    properties.put(key, SystemTools.getDbInitSize());
                } else if ("jdbc.initialSize".equals(key) && SystemTools.isWindows()) {
                    properties.put(key, SystemTools.getDbInitSize());
                } else if ("jdbc.minIdle".equals(key) && SystemTools.isWindows()) {
                    properties.put(key, SystemTools.getDbInitSize());
                } else {
                    properties.put(key, value);
                }
            }

            return properties;
        }
    }

    private static String getParentValue(List<Properties> ps, String key, String value) {
        int i = 0;

        for(String pValue = null; i < ps.size(); ++i) {
            Properties p = (Properties)ps.get(i);
            pValue = p.getProperty(key);
            if (pValue != null && pValue.length() > 0) {
                pValue = pValue.trim();
                if (value.length() == 0) {
                    value = pValue;
                } else if (value.contains("$parent$")) {
                    value = value.replace("$parent$", pValue);
                }

                if (!value.contains("$parent$")) {
                    break;
                }
            }
        }

        return value;
    }

    public static void modifyProperties(String projectName, String propertiesName, String path, String targetName) {
        Properties sourceProp = createProperties(projectName, propertiesName);
        Properties dubboProp = new Properties();
        targetName = path + targetName + ".properties";

        try {
            FileInputStream is = new FileInputStream(targetName);
            dubboProp.load(is);
        } catch (Exception var12) {
            File file = new File(targetName);

            try {
                file.createNewFile();
            } catch (IOException var11) {
                var11.printStackTrace();
            }
        }

        try {
            Iterator it = sourceProp.entrySet().iterator();

            while(it.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry)it.next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                dubboProp.setProperty(key, value);
            }

            OutputStream fos = new FileOutputStream(targetName);
            dubboProp.store(fos, "");
            fos.close();
        } catch (FileNotFoundException var13) {
            var13.printStackTrace();
        } catch (IOException var14) {
            var14.printStackTrace();
        }

    }

    private static List<Properties> getParent(String path, String propertiesName) {
        ArrayList<Properties> ps = new ArrayList();
        Properties p;
        if (path != null && path.length() != 0) {
            for(int i = path.lastIndexOf("/"); i > 0; i = path.substring(0, i).lastIndexOf(47)) {
                String dir = path.substring(0, i) + "/common";
                p = getParentProperties(dir, propertiesName);
                if (p != null) {
                    ps.add(p);
                }
            }

            p = getParentProperties(propertiesName);
            if (p != null) {
                ps.add(p);
            }

            return ps;
        } else {
            p = getParentProperties(propertiesName);
            if (p != null) {
                ps.add(p);
            }

            return ps;
        }
    }

    private static Properties getParentProperties(String propertiesName) {
        return getParentProperties("common", propertiesName);
    }

    private static Properties getParentProperties(String pathName, String propertiesName) {
        String parentPath = pathName + "/" + propertiesName;

        try {
            ResourceBundle prb = ResourceBundle.getBundle(parentPath.trim());
            Properties properties = new Properties();
            Enumeration pkeys = prb.getKeys();

            while(pkeys.hasMoreElements()) {
                String key = (String)pkeys.nextElement();
                String value = prb.getString(key).trim();
                properties.put(key, value);
            }

            return properties;
        } catch (Exception var8) {
            return null;
        }
    }

    public static void main(String[] args) {
        Properties properties = createProperties("iesp/base", "dbConfig");
        Enumeration keys = properties.keys();

        while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            System.out.println(key + "=" + properties.getProperty(key));
        }

    }
}
