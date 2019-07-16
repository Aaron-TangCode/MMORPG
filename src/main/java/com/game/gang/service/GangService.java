package com.game.gang.service;

import com.game.gang.bean.GangMemberEntity;
import com.game.gang.repository.GangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName GangService
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/16 16:21
 * @Version 1.0
 */
@Service
public class GangService {
    @Autowired
    private GangRepository gangRepository;

    public GangMemberEntity findGangMember(int roleId) {
        return gangRepository.findGangMember(roleId);
    }
}
