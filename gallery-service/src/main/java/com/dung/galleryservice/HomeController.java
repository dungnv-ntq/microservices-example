package com.dung.galleryservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/gallery")
public class HomeController {
    private static final Logger LOG = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @GetMapping("/")
    public String home() {
        return "Hello from Gallery service running at: " + env.getProperty("local.server.port");
    }

    @GetMapping("/{id}")
    @HystrixCommand(fallbackMethod = "fallback")
    public Gallery getGallery(@PathVariable String id) {
        LOG.log(Level.INFO,"get gallery by id");
        Gallery gallery = new Gallery();
        gallery.setId(id);

        @SuppressWarnings("unchecked") // It will throw exception from image service when image service down
        List<Object> images = restTemplate.getForObject("http://image-service/image/", List.class);
        gallery.setImages(images);

        return gallery;
    }

    // a fallback method to be called if failure happened
    public Gallery fallback(String galleryId, Throwable hystrixCommand) {
        Gallery newOne = new Gallery();
        newOne.setId(galleryId);
        return newOne;
    }
}
