package com.wednesday.kanban.web.Index.Controller.permission;

import com.wednesday.kanban.domain.Perm;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.permission.session.SessionUtils;

import javax.servlet.http.HttpServletRequest;

public class PermissionUtils {
    public static boolean hasPower(HttpServletRequest request,Perm perm) {
        UserInstance userInstance = SessionUtils.getUser(request);

        for(Perm p : userInstance.getPerms()) {
            if(p.getCode().equals(perm.getCode())) {
                return true;
            }
        }

        return false;
    }
}
