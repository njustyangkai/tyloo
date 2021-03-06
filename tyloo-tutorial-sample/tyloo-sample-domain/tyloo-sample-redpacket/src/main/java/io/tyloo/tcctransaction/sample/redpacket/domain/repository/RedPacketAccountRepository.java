package io.tyloo.tcctransaction.sample.redpacket.domain.repository;


import io.tyloo.tcctransaction.sample.exception.InsufficientBalanceException;
import io.tyloo.tcctransaction.sample.redpacket.domain.entity.RedPacketAccount;
import io.tyloo.tcctransaction.sample.redpacket.infrastructure.dao.RedPacketAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*
 *
 * @Author:Zh1Cheung 945503088@qq.com
 * @Date: 9:31 2019/12/5
 *
 */
@Repository
public class RedPacketAccountRepository {

    @Autowired
    RedPacketAccountDao redPacketAccountDao;

    public RedPacketAccount findByUserId(long userId) {

        return redPacketAccountDao.findByUserId(userId);
    }

    public void save(RedPacketAccount redPacketAccount) {
        int effectCount = redPacketAccountDao.update(redPacketAccount);
        if (effectCount < 1) {
            throw new InsufficientBalanceException();
        }
    }
}
