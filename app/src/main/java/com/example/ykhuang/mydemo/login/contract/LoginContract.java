package com.example.ykhuang.mydemo.login.contract;

/**
 * Created by hyk on 2018/8/8.
 */

public class LoginContract {
    public interface LoginView<LoginPresenter>{
        void wxCallBack();
    }

    public interface LoginPresenter{
        void loginByWx();
    }
}
