package com.fc.mapper;

import com.fc.model.Info;
import com.fc.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


public interface UserMapper {

    int selectEmailCount(String email);

    void insertUser(User user);

    int selectActived(User user);

    User selectUserByUid(Long uid);

    List<User> selectUserByUids(List<String> users);

    //这里有点特殊
    Long selectUidByEmailAndPassword(User user);

    User selectEditInfo(int uid);

    void updateUser(User user);

    void updatePostCount(Long uid);

    void updateActived(String activateCode);

    void insertInfo(Info info);

    List<User> listUserByTime();

    List<User> listUserByHot();

    void updateHeadUrl(@Param("uid") int uid, @Param("headUrl") String headUrl);

    String selectHeadUrl(Long uid);

    void updateScanCount(Long uid);

    User selectUsernameByUid(Long uid);

    String selectPasswordByUid(int uid);

    void updatePassword(@Param("newPassword") String newPassword,@Param("uid") int uid);

    String selectVerifyCode(String email);

    void updatePasswordByActivateCode(String code);

    List<User> getAllUser();

    String randomMessage();
}
