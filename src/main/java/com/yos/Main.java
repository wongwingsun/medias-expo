package com.yos;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        String prod2MdmMappingStr;
        String prod2UrlsMappingStr;
        String filePath4Prod2MdmMappings = "/Volumes/Projects/data/medias-expo/prod2Mdm.csv";
        String filePath4Prod2UrlsMappings = "/Volumes/Projects/data/medias-expo/prod2Urls.csv";
        String folderPathPrefix4MdmMedias = "/Volumes/Projects/data/medias-expo/mdm/";
        String urlPrefix = "http://image.yos168.com";
        Map<String, String> prod2MdmMapping = new HashMap<>();
        BufferedReader prod2MdmMappingReader = new BufferedReader(new FileReader(filePath4Prod2MdmMappings));
        BufferedReader prod2UrlsMappingReader = new BufferedReader(new FileReader(filePath4Prod2UrlsMappings));
        while (!Strings.isNullOrEmpty(prod2MdmMappingStr = prod2MdmMappingReader.readLine())) {
            String[] prod2MdmMappingArg = prod2MdmMappingStr.split(",");
            if (prod2MdmMappingArg.length >= 2) {
                prod2MdmMapping.put(prod2MdmMappingArg[1], prod2MdmMappingArg[0]);
            }
        }
        while (!Strings.isNullOrEmpty(prod2UrlsMappingStr = prod2UrlsMappingReader.readLine())) {
            String[] prod2UrlsMappingArg = prod2UrlsMappingStr.split(",");
            if (prod2UrlsMappingArg.length >= 2) {
                String mdmCode = prod2MdmMapping.get(prod2UrlsMappingArg[0]);
                File mdmFolder = new File(folderPathPrefix4MdmMedias + mdmCode);
                if (!mdmFolder.exists()) {
                    mdmFolder.mkdir();
                }
                for (int i = 1; i < prod2UrlsMappingArg.length; i++) {
                    String uri = prod2UrlsMappingArg[i];
                    FileUtils.copyURLToFile(new URL(urlPrefix + uri), new File(mdmFolder, mdmCode + "_" + String.format("%02d", i) + uri.substring(uri.lastIndexOf("."))));
                }
            }
        }
    }
}
