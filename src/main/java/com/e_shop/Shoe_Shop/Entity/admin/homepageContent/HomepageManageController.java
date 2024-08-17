package com.e_shop.Shoe_Shop.Entity.admin.homepageContent;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/admin/managehomepage")
public class HomepageManageController {

    private final HomepageManageService homepageManageService;
    
    public HomepageManageController(HomepageManageService homepageManageService) {
        this.homepageManageService = homepageManageService;
    }

    @GetMapping("/getfilenames")
    public FileNameResponse getHomepageBanners_PosterFileNames() {
        return homepageManageService.getHomepageBanners_PosterFileNames();
    }

    @PostMapping("/changebanners")
    public String changeBanners(MultipartFile[] files) {
        return homepageManageService.changeBanners(files);
    }

    @PostMapping("/changeposters")
    public String changePosters(MultipartFile[] files) {
        return homepageManageService.changePosters(files);
    }

    @PostMapping("/changesample1")
    public String changeSample1(MultipartFile files) {
        return homepageManageService.changeSample(files, 1);
    }

    @PostMapping("/changesample2")
    public String changeSample2(MultipartFile files) {
        return homepageManageService.changeSample(files, 2);
    }
}
