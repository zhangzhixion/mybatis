package com.powernode.bank.dao;

import com.powernode.bank.pojo.Account;

/**
 * 账户的dao对象
 */
public interface AccountDao {
    /**
     * 根据账号查询账户信息
     * @param actno
     * @return
     */
    Account selectByActno(String actno);

    /**
     * 更新账户信息
     * @param act
     * @return
     */
    int updateByActno(Account act);
}
