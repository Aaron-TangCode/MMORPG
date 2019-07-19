package com.game.gang.service;

import com.game.gang.bean.GangEntity;
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

    public void insertGang(String gangName) {
        gangRepository.insertGang(gangName);
    }

    public GangEntity queryGang(String gangName) {
        return gangRepository.queryGang(gangName);
    }

    public void insertGangMember(GangMemberEntity entity) {
        gangRepository.insertGangMember(entity);
    }

    public GangEntity queryGangByRoleName(Integer roleId) {
        return gangRepository.queryGangByRoleName(roleId);
    }

    public void updateGangEntity(GangEntity gangEntity) {
        gangRepository.updateGangEntity(gangEntity);
    }
}
