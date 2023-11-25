package com.powernode.bank.service.impl;

import com.powernode.bank.dao.AccountDao;
import com.powernode.bank.dao.impl.AccountDaoImpl;
import com.powernode.bank.exceptions.MoneyNotEnoughException;
import com.powernode.bank.exceptions.TransferException;
import com.powernode.bank.pojo.Account;
import com.powernode.bank.service.AccountService;
import com.powernode.bank.utils.GenerateDaoProxy;
import com.powernode.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountServiceImpl implements AccountService {
    //private AccountDao accountDao = new AccountDaoImpl();
    //自己封装的动态有问题
    //private AccountDao accountDao = (AccountDao) GenerateDaoProxy.generate(SqlSessionUtil.openSession(), AccountDao.class);

    private AccountDao accountDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);

    @Override
    public void transfer(String fromActno, String toActno, double money) throws MoneyNotEnoughException, TransferException {
        //添加事务控制代码
        SqlSession sqlSession = SqlSessionUtil.openSession();

        //1.判断账户的余额是否有这么多
        Account fromAct = accountDao.selectByActno(fromActno);
        if (fromAct.getBalance() < money){
            //2.如果没有这么多就提示用户
            throw new MoneyNotEnoughException("对不起，余额不足");
        }
        //3.如果账户有这么多就更新
        //先更新实体类中的数据
        Account toAct = accountDao.selectByActno(toActno);
        fromAct.setBalance(fromAct.getBalance() - money);
        toAct.setBalance(toAct.getBalance() + money);
        int count = accountDao.updateByActno(fromAct);
        //模拟异常
        //String s = null;
        //s.toString();

        count += accountDao.updateByActno(toAct);
        if (count != 2){
            throw new TransferException("转账异常，未知原因");
        }

        //提交事务
        sqlSession.commit();
        //关闭事务
        SqlSessionUtil.close(sqlSession);
    }
}
