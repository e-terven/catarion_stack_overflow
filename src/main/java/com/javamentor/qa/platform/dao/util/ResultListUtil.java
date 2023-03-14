package com.javamentor.qa.platform.dao.util;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResultListUtil {
    public static <T> List<T> getSetResultOrNull(TypedQuery<T> var) {
        try {
            return new ArrayList<T>(var.getResultList());
        } catch (Exception e) {
            return List.of();
        }
    }
    public static <T> List<T> getSetResultOrNull(Query var) {
        try {
            return new ArrayList<T>((Collection<? extends T>) var.getResultList()) {
            };
        } catch (Exception e) {
            return List.of();
        }
    }
}
