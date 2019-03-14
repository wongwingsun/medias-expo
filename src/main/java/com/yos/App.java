package com.yos;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        String filePath4Prod2MdmMappings = "/Volumes/Projects/data/medias-expo/prod2Mdm.csv";
        String folderPathPrefix4ProdMedias = "/Volumes/Projects/data/medias-expo/prod/";
        String folderPathPrefix4MdmMedias = "/Volumes/Projects/data/medias-expo/mdm/";
        String prod2MdmMappingStr;
        BufferedReader reader = new BufferedReader(new FileReader(filePath4Prod2MdmMappings));
        while (!Strings.isNullOrEmpty(prod2MdmMappingStr = reader.readLine())) {
            String[] prod2MdmMapping = prod2MdmMappingStr.split(",");
            if (prod2MdmMapping.length >= 2) {
                String productCode = prod2MdmMapping[1];
                String mdmCode = prod2MdmMapping[0];
                File prodMediasFolder = new File(folderPathPrefix4ProdMedias + productCode + "/images");
                if (prodMediasFolder.exists()) {
                    File mdmMediasFolder = new File(folderPathPrefix4MdmMedias + mdmCode);
                    if (!mdmMediasFolder.exists()) {
                        mdmMediasFolder.mkdir();
                    }
                    File[] prodMedias = prodMediasFolder.listFiles();
                    if (prodMedias != null) {
                        for (int i = 0; i < prodMedias.length; i++) {
                            File prodMedia = prodMedias[i];
                            String prodMediaName = prodMedia.getName();
                            FileUtils.copyFile(prodMedia, new File(mdmMediasFolder, mdmCode + "_" + String.format("%02d", i + 1) + prodMediaName.substring(prodMediaName.lastIndexOf("."))));
                        }
                    }
                }
            }
        }
    }
}
