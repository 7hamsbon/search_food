package com.ham.service;

import com.ham.entity.Music;
import com.ham.vo.OpResult;

import java.util.List;

/**
 * Created by hamsbon on 2017/4/13.
 */
public interface MusicService {

    /**
     * 新增音乐
     */
    OpResult<Music> insertMusic(Music music);

    /**
     * 根据拥有者id获取音乐
     */
    OpResult<List<Music>> getMusics(Long id);

}
