package ch.ethz.inf.vs.android.gamarc.ownpass;

import java.util.List;

public interface UserPasswordCallback {
    public void onSuccess(List<Password> passwordList);
    public void onError(Exception exception);
}
