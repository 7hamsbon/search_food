package com.ham.service.impl;

import com.ham.dao.MusicMapper;
import com.ham.entity.Music;
import com.ham.entity.MusicExample;
import com.ham.service.MusicService;
import com.ham.vo.OpResult;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/4/13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public OpResult<Music> insertMusic(Music music) {
        OpResult<Music> opResult = new OpResult<>(false,null,"新增音乐失败");
        if(StringUtils.isNullOrEmpty(music.getFilePath())){
            opResult.setOpMsg("音乐路径不存在");
        }else{
            if(musicMapper.insertSelective(music)>0){
                opResult.setSuccess(true);
                opResult.setData(music);
                opResult.setOpMsg("");
            }
        }

        return opResult;
    }

    @Override
    public OpResult<List<Music>> getMusics(Long id) {
        OpResult<List<Music>> opResult = new OpResult<>(false,null,"获取音乐列表失败");
        if(id!=null){
            MusicExample example = new MusicExample();
            MusicExample.Criteria criteria = example.createCriteria();
            criteria.andOwnerIdEqualTo(id);
            List<Music> l = musicMapper.selectByExample(example);
            opResult.setSuccess(true);
            opResult.setData(l);
            opResult.setOpMsg("");
        }
        return opResult;
    }
}
