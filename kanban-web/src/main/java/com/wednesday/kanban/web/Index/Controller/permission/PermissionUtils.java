package com.wednesday.kanban.web.Index.Controller.permission;

import com.wednesday.kanban.domain.Perm;
import com.wednesday.kanban.domain.UserInstance;

import javax.servlet.http.HttpServletRequest;

import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.LOGIN_IDENTITY;
import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.parseToken;

public class PermissionUtils {
    public static boolean hasPower(HttpServletRequest request,Perm perm) {
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY);

        UserInstance userInstance = parseToken(cookieToken);

        for(Perm p : userInstance.getPerms()) {
            if(p.getCode().equals(perm.getCode())) {
                return true;
            }
        }

        return false;
    }
}
