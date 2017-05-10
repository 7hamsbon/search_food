package com.ham.web;

import com.ham.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hamsbon on 2017/4/13.
 */
@RestController
public class MusicCotroller {

    @Autowired
    MusicService musicService;

}
