package com.e_shop.Shoe_Shop.Config.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HomepageManageService {

    public FileNameResponse getHomepageBanners_PosterFileNames() {
        String bannerDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/public/img/homepage/banner";
        String posterDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/public/img/homepage/poster";
        String sampleDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/public/img/homepage/sample";
        
        File bannerDirFile = new File(bannerDir);
        if (!bannerDirFile.exists()) {
            bannerDirFile.mkdirs();
        }
        File posterDirFile = new File(posterDir);
        if (!posterDirFile.exists()) {
            posterDirFile.mkdirs();
        }
        File sampleDirFile = new File(sampleDir);
        if (!sampleDirFile.exists()) {
            sampleDirFile.mkdirs();
        }

        String bannerfileNames = Arrays.stream(bannerDirFile.listFiles())
            .map(File::getName)
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.joining(" "));
        String posterfileNames = Arrays.stream(posterDirFile.listFiles())
            .map(File::getName)
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.joining(" "));
        String samplefileNames = Arrays.stream(sampleDirFile.listFiles())
            .map(File::getName)
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.joining(" "));

        FileNameResponse fileNameResponse = new FileNameResponse(bannerfileNames, posterfileNames, samplefileNames);
        return fileNameResponse;
    }

    public String changeBanners(MultipartFile[] files) {
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/public/img/homepage/banner";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to create upload directory");
        }

        File[] existingFiles = uploadDirFile.listFiles();
        if (existingFiles != null) {
            for (File file : existingFiles) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String newFileName = "banner" + (i + 1) + "." + extension;
            String filePath = uploadDir + "/" + newFileName;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to upload image: " + newFileName);
            }
        }

        String fileNames = Arrays.stream(uploadDirFile.listFiles())
        .map(File::getName)
        .sorted(Comparator.naturalOrder())
        .collect(Collectors.joining(" "));

        return fileNames;
    }

    public String changePosters(MultipartFile[] files) {
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/public/img/homepage/poster";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to create upload directory");
        }

        File[] existingFiles = uploadDirFile.listFiles();
        if (existingFiles != null) {
            for (File file : existingFiles) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String newFileName;
            if(i == 0) {
                newFileName = "poster-main." + extension;
            } else { 
                newFileName = "poster" + (i + 1) + "." + extension;
            }
            String filePath = uploadDir + "/" + newFileName;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to upload image: " + newFileName);
            }
        }

        String fileNames = Arrays.stream(uploadDirFile.listFiles())
            .map(File::getName)
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.joining(" "));

        return fileNames;
    }

    public String changeSample(MultipartFile image, int index) {
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/public/img/homepage/sample";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to create upload directory");
        }

        File[] existingFiles = uploadDirFile.listFiles();
        if (existingFiles != null) {
            for (File file : existingFiles) {
                if (file.isFile()) {
                    if(index == 1 && file.getName() == "sample1")
                        file.delete();
                    if(index == 2 && file.getName() == "sample2")
                        file.delete();
                }
            }
        }
        
        MultipartFile file = image;
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String newFileName = "sample" + index + "." + extension;
        String filePath = uploadDir + "/" + newFileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to upload image: " + newFileName);
        }
        

        String fileNames = Arrays.stream(uploadDirFile.listFiles())
        .map(File::getName)
        .sorted(Comparator.naturalOrder())
        .collect(Collectors.joining(" "));

        return fileNames;
    }

}
