package com.fc.commons.status;

import com.fc.commons.redis.RedisKey;

/**
 * 帖子列表类型
 * 最近
 * 最热
 * 精华
 */
public enum PostListType {

    RECENTLY{
        @Override
        public Integer getValue() {
            return 1;
        }

        @Override
        public String getName() {
            return RedisKey.RECENTLY_POST_LIST;
        }
    },
    LIVELY{
        @Override
        public Integer getValue() {
            return 2;
        }

        @Override
        public String getName() {
            return RedisKey.LIVELY_POST_LIST;
        }
    },
    ESSENCE{
        @Override
        public Integer getValue() {
            return 3;
        }

        @Override
        public String getName() {
            return RedisKey.ESSENCE_POST_LIST;
        }
    };

    public abstract Integer getValue();

    public abstract String getName();

    public static String postListTypeName(Integer value) {
        if (PostListType.ESSENCE.getValue() == value) {
            return PostListType.ESSENCE.getName();
        } else if (PostListType.LIVELY.getValue() == value) {
            return PostListType.LIVELY.getName();
        } else if (PostListType.RECENTLY.getValue() == value) {
            return PostListType.RECENTLY.getName();
        } else {
            return "";
        }
    }
}
