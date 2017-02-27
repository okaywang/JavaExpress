package di.ctxannotation;

import org.springframework.stereotype.Component;

/**
 * foo...Created by wgj on 2017/2/26.
 */
@Component
public class UserDaoImpl implements UserDao {
    @Override
    public int maxUserId() {
        return 1001;
    }
}
