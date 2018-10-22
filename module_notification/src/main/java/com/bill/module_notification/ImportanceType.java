package com.bill.module_notification;

import android.support.annotation.IntDef;

@IntDef({ImportanceType.IMPORTANCE_NONE,
        ImportanceType.IMPORTANCE_MIN,
        ImportanceType.IMPORTANCE_LOW,
        ImportanceType.IMPORTANCE_DEFAULT,
        ImportanceType.IMPORTANCE_HIGH})
public @interface ImportanceType {

    int IMPORTANCE_NONE = 0;
    int IMPORTANCE_MIN = 1;
    int IMPORTANCE_LOW = 2;
    int IMPORTANCE_DEFAULT = 3;
    int IMPORTANCE_HIGH = 4;
}
