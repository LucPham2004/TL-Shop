package com.e_shop.Shoe_Shop.Entity.admin.homepageContent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HomepageManageService {

    public HomepageManageService() {
        
    }

    public FileNameResponse getHomepageBanners_PosterFileNames() {
        String bannerDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/homepage/banner";
        String posterDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/homepage/poster";
        String sampleDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/homepage/sample";
        
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

        // Kiểm tra nếu không có file nào trong thư mục
        String bannerfileNames = (bannerDirFile.listFiles() != null) ? 
            Arrays.stream(bannerDirFile.listFiles())
                .map(File::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(" ")) 
            : "";

        String posterfileNames = (posterDirFile.listFiles() != null) ? 
            Arrays.stream(posterDirFile.listFiles())
                .map(File::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(" ")) 
            : "";

        String samplefileNames = (sampleDirFile.listFiles() != null) ? 
            Arrays.stream(sampleDirFile.listFiles())
                .map(File::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(" ")) 
            : "";

        // Debug output để kiểm tra kết quả
        System.out.println("Banner file names: " + bannerfileNames);
        System.out.println("Poster file names: " + posterfileNames);
        System.out.println("Sample file names: " + samplefileNames);

        return new FileNameResponse(bannerfileNames, posterfileNames, samplefileNames);
    }

    public String changeBanners(MultipartFile[] files) {
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/homepage/banner";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
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
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/homepage/poster";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
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
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/homepage/sample";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
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
